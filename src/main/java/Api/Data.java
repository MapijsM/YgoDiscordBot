package Api;

import java.util.List;

public class Data {
    private List<Card> data;

    public Data(List<Card> data) {
        this.data = data;
    }

    public List<Card> getData() {
        return data;
    }

    public void setData(List<Card> data) {
        this.data = data;
    }
}