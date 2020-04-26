package Model;




public enum Map {


    BLUE("/View/Space2.jpg"),
    GREEN("/View/level1.jpg");

    String url;

    private Map(String url) {
        this.url = url;

    }

    public String getUrl() {
        return url;
    }


}
