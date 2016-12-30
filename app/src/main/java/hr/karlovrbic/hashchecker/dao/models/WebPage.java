package hr.karlovrbic.hashchecker.dao.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import hr.karlovrbic.hashchecker.dao.HashDatabase;

/**
 * Class {@code WebPage} is POJO class that contains information about web page URL and hashcode(SHA-1) of it's content.
 * It also serves as a database table.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 */
@Table(database = HashDatabase.class, name = HashDatabase.WEB_PAGE_TABLE)
public final class WebPage extends BaseModel implements Parcelable {

    public static final Creator<WebPage> CREATOR = new Creator<WebPage>() {
        @Override
        public WebPage createFromParcel(Parcel source) {
            return new WebPage(source);
        }

        @Override
        public WebPage[] newArray(int size) {
            return new WebPage[size];
        }
    };

    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    private long id;
    @Column(name = "url")
    @Unique(onUniqueConflict = ConflictAction.FAIL)
    private String url;
    @Column(name = "hashcode")
    private String hashcode;

    public WebPage(String url, String hashcode) {
        this.url = url;
        this.hashcode = hashcode;
    }

    public WebPage() {
        this(null, null);
    }

    protected WebPage(Parcel in) {
        this.id = in.readLong();
        this.url = in.readString();
        this.hashcode = in.readString();
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public void setHashcode(@NonNull String hashcode) {
        this.hashcode = hashcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.url);
        dest.writeString(this.hashcode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WebPage webPage = (WebPage) o;

        return new EqualsBuilder()
                .append(id, webPage.id)
                .append(url, webPage.url)
                .append(hashcode, webPage.hashcode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(url)
                .append(hashcode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "WebPage{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", hashcode='" + hashcode + '\'' +
                '}';
    }
}
