package Model;

public class Data {
    private String datasetName;
    private String fileName;
    private String mysqlServer;
    private String filePath;

    public Data(String datasetName, String fileName, String mysqlServer, String filePath) {
        this.datasetName = datasetName;
        this.fileName = fileName;
        this.mysqlServer = mysqlServer;
        this.filePath = filePath;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMysqlServer() {
        return mysqlServer;
    }

    public String getFilePath() {
        return filePath;
    }
}