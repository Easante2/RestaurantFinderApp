package uk.ac.aston.restaurantfinderapp.Model;

import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Emmanuel on 25/02/2016.
 */
public class Eatery implements Serializable {
    private static final long serialVersionUID = 8439445017702637758L;
    private String title;
    private String description;
    private URL link;
    private Date date;
    private URL thumb;
    private boolean isRead;
    private GeoRssLocation location;


    @Override
    public boolean equals(Object o) {
        if (o instanceof Eatery) {
            if (this == o) return true;
            else {
                Eatery other = (Eatery) o;
                return this.title.equals(other.getTitle()) && this.date.equals(other.getDate())
                        && this.getDescription() == other.getDescription() &&
                        this.link.equals(other.getLink()) &&
                        this.location.equals(other.getLocation());
            }

        } else {
            return false;
        }
    }

    public Eatery() {
        isRead = false;
    }



    public GeoRssLocation getLocation() {
        if (location == null) {
            return null;
        } else {
            return location;
        }
    }



    public void setLocation(GeoRssLocation location) {
        this.location = location;
    }


    public String toString() {
        return title;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder(title);
        sb.append("\n");
        sb.append(description);
        sb.append("\n");
        sb.append(link.toString());
        sb.append("\nFrom the news on: ");
        sb.append(this.getDate());
        return sb.toString();
    }

    public String toString1() {
        StringBuilder sb = new StringBuilder("<item>\n");
        sb.append("<title>");
        sb.append(title);
        sb.append("</title>\n");
        sb.append("<description>");
        sb.append(description);
        sb.append("</description>\n");
        sb.append("<link>");
        sb.append(link.toString());
        sb.append("</link>\n");
        sb.append("<date>");
        sb.append(date);
        sb.append("</date>\n");
        if (thumb != null) {
            sb.append("<media:thumb>");
            sb.append(thumb.toString());
            sb.append("</media:thumb>\n");
        }
        sb.append("</item>\n");
        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "EEE, d MMM yyyy HH:mm:ss z", Locale.UK);
        return sdf.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public URL getThumb() {
        return thumb;
    }

    public void setThumb(URL thumb) {
        this.thumb = thumb;
    }



}
