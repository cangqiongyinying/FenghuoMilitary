package phone1000.com.firemilitary.easytagdragview.bean;

public class SimpleTitleTip implements Tip {
    private int id;
    private String tip;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "tip:"+ tip;
    }
}
