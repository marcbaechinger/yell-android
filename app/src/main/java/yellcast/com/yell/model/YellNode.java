package yellcast.com.yell.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class YellNode implements Parcelable {
    private String uuid;
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


    private YellNode(String uuid) {
        this.uuid = uuid;
    }

    public static final YellNode newInstance() {
        return new YellNode(UUID.randomUUID().toString());
    }

    public YellNode(Parcel in) {
        uuid = in.readString();
        label = in.readString();
        url = in.readString();
        String typeName = in.readString();
        if (typeName != null) {
            type = YellNodeType.valueOf(typeName);
        }
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(label);
        parcel.writeString(url);
        parcel.writeString(this.type == null ? null : this.type.name());
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
        if (uuid != null) {
            json.put("uuid", uuid);
        }
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
        if (uuid != null ? !uuid.equals(yellNode.uuid) : yellNode.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (uuid != null ? uuid.hashCode() : 0);
        return result;
    }
}
