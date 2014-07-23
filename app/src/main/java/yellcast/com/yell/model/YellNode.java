package yellcast.com.yell.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marcbaechinger on 22.07.14.
 */
public class YellNode implements Parcelable {
    private String label;
    private String url;
    private YellNodeType type;

    public static final Parcelable.Creator<YellNode> CREATOR = new Parcelable.Creator<YellNode>() {
        public YellNode createFromParcel(Parcel in) {
            return new YellNode(in);
        }

        public YellNode[] newArray(int size) {
            return new YellNode[size];
        }
    };


    public YellNode() {}

    public YellNode(Parcel in) {
        label = in.readString();
        url = in.readString();
        String typeName = in.readString();
        if (typeName != null) {
            type = YellNodeType.valueOf(typeName);
        }
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(label);
        parcel.writeString(url);
        parcel.writeString(this.type == null ? null : this.type.name());
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public YellNodeType getType() {
        return type;
    }

    public void setType(YellNodeType type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        if (label != null) {
            json.put("label", label);
        }
        if (url!= null) {
            json.put("url", url);
        }
        if (type != null) {
            json.put("type", type.name());
        }
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YellNode yellNode = (YellNode) o;

        if (type != yellNode.type) return false;
        if (url != null ? !url.equals(yellNode.url) : yellNode.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (url != null ? url.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
