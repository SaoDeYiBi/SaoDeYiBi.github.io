import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DirectoryCheck {

    private final static DirectoryCheck obj = new DirectoryCheck();

    private final static String ROOT_PATH = "D:/workshop/SaoDeYiBi.github.io/main/";
    private final static String BLOG = ROOT_PATH + "blog/";
    private final static String RESOURCES = ROOT_PATH + "resources/";
    private final static String JAVA = ROOT_PATH + "webapp/java/";

    private final static Map<String, List<FileObj>> dirContextMap;

    static {
        dirContextMap = new ConcurrentHashMap<>(3);
        dirContextMap.put(BLOG, new LinkedList<>());
        dirContextMap.put(RESOURCES, new LinkedList<>());
        dirContextMap.put(JAVA, new LinkedList<>());
    }

    public static void main(String[] args) {
        obj.doCheck();
    }

    private void doCheck() {
        initDirInfo();
        System.out.println(dirContextMap);

    }

    private void initDirInfo() {
        long startGetDirs = System.currentTimeMillis();
        getDirs(BLOG, BLOG);
        getDirs(RESOURCES, RESOURCES);
        getDirs(JAVA, JAVA);
        long endGetDirs = System.currentTimeMillis();
        System.out.println("Get dirs use: " + (endGetDirs - startGetDirs) + " ms.");
    }

    private void getDirs(String absolutePath, String root) {
        Objects.requireNonNull(absolutePath);
        Objects.requireNonNull(root);
        getDirs(new File(absolutePath), root);
    }

    private void getDirs(File file, String root) {
        Objects.requireNonNull(file);
        boolean exists = file.exists();
        boolean directory = file.isDirectory();
        if (exists && directory) {
            File[] content = file.listFiles();
            if (null != content && content.length > 0) {
                Arrays.stream(content).forEach(subFile -> {
                    boolean isDir = subFile.isDirectory();
                    if (isDir) {
                        List<FileObj> fileObjList = dirContextMap.get(root);
                        if (null != fileObjList) {
                            fileObjList.add(new FileObj(subFile));
                        }
                        getDirs(subFile, subFile.getAbsolutePath());
                    }
                });
            }
        }
    }

    private class FileObj {

        private String absolutePath;

        private String name;

        private String parentAbsolutePath;

        private String parentName;

        FileObj(File file) {
            this.absolutePath = file.getAbsolutePath();
            this.name = file.getName();
            this.parentAbsolutePath = file.getParentFile().getAbsolutePath();
            this.parentName = file.getParentFile().getName();
        }

        @Override
        public String toString() {
            return "\r\nFileObj{" +
                    "absolutePath='" + absolutePath + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
