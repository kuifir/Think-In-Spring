package kuifir.i18n;

import jdk.nashorn.internal.ir.CallNode;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.Executors;

/**
 * Package: kuifir.i18n
 * <p>
 * Description： 动态更新资源{@link org.springframework.context.MessageSource} 实现
 * 实现步骤
 * 1. 定位资源位置（ Properties 文件）
 * 2. 初始化 Properties 对象
 * 3. 实现 AbstractMessageSource#resolveCode 方法
 * 4. 监听资源文件（Java NIO 2 WatchService）
 * 5. 使用线程池处理文件变化
 * 6. 重新装载 Properties 对象
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/6 20:10
 * <p>
 * Version: 0.0.1
 *
 * @see org.springframework.context.MessageSource
 * @see AbstractMessageSource
 */
public class DynamicResourceMessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    private static String resourceFileName = "msg.properties";
    private static String resourceFileDir = "/META-INF/";
    private static String encoding = "GBK";

    private Properties messageProperties;
    private ResourceLoader resourceLoader;
    private Resource messagePropertiesResource;

    public DynamicResourceMessageSource() {
        messagePropertiesResource = getMessagePropertiesResource();
        messageProperties = getProperties();
        // 监听资源文件（Java NIO 2 WatchService）
        onMessagePropertiesChanged();
    }

    private void onMessagePropertiesChanged() {
        if (this.messagePropertiesResource.isFile()) { // 判断是否为文件
            // 获取对应文件系统中的文件
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Path path = messagePropertiesResource.getFile().toPath();
                path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
                Executors.newSingleThreadExecutor().submit(() -> {
                    while (true) {
                        WatchKey watchKey = watchService.take();
                        try {
                            if (watchKey.isValid()){
                                for (WatchEvent<?> event : watchKey.pollEvents()) {
                                    Watchable watchable = watchKey.watchable();
                                    // 目录路径（监听的注册目录）
                                    Path dirPath = (Path) watchable;
                                    // 事件所关联的对象即注册目录的子文件（或子目录）
                                    // 事件发生源是相对路径
                                    Path fileRelativePath = (Path) event.context();
                                    if (resourceFileName.equals(fileRelativePath.getFileName().toString())) {
                                        // 处理为绝对路径
                                        Path filePath = dirPath.resolve(fileRelativePath);
                                        Properties properties = loadMessageProperties(Files.newBufferedReader(filePath, Charset.forName("GBK")));
                                        synchronized (messageProperties) {
                                            messageProperties.clear();
                                            messageProperties.putAll(properties);
                                        }
                                    }
                                }
                            }
                        } finally {
                            if (watchKey != null) {
                                watchKey.reset(); // 重置 WatchKey
                            }
                        }
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    private Properties getProperties() {
        Resource resource = getMessagePropertiesResource();
        EncodedResource encodedResource = new EncodedResource(resource, encoding);
        try (Reader reader = encodedResource.getReader()) {
            return loadMessageProperties(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Resource getMessagePropertiesResource() {
        return getResourceLoader().getResource(resourceFileDir + resourceFileName);
    }

    private Properties loadMessageProperties(Reader reader) {
        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String messageFormatPattern = messageProperties.getProperty(code);
        if (StringUtils.hasText(messageFormatPattern)) {
            return new MessageFormat(messageFormatPattern, locale);
        }
        return null;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader == null ? new DefaultResourceLoader() : resourceLoader;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void setResourceFileName(String resourceFileName) {
        DynamicResourceMessageSource.resourceFileName = resourceFileName;
    }

    public static void setResourceFileDir(String resourceFileDir) {
        DynamicResourceMessageSource.resourceFileDir = resourceFileDir;
    }

    public static void setEncoding(String encoding) {
        DynamicResourceMessageSource.encoding = encoding;
    }

    public static void main(String[] args) throws InterruptedException {
        DynamicResourceMessageSource.setEncoding("GBK");
        DynamicResourceMessageSource messageSource = new DynamicResourceMessageSource();
        for (int i = 0; i < 10000; i++) {
            String message = messageSource.getMessage("name", new Object[]{}, Locale.getDefault());
            System.out.println(message);
            Thread.sleep(1000L);
        }
    }
}
