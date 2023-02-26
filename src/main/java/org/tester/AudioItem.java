package org.tester;


import java.io.Serializable;

public class AudioItem implements Serializable {
    private Double id;
    private String artistName;
    private String trackTitle;
    private String albumTitle;
    private Integer trackNumber;
    private Integer year;
    private Integer numberOfReiview;
    private Integer numberOfCopiesSold;

    public Double getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getNumberOfReiview() {
        return numberOfReiview;
    }

    public Integer getNumberOfCopiesSold() {
        return numberOfCopiesSold;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setNumberOfReiview(Integer numberOfReiview) {
        this.numberOfReiview = numberOfReiview;
    }

    public void setNumberOfCopiesSold(Integer numberOfCopiesSold) {
        this.numberOfCopiesSold = numberOfCopiesSold;
    }
}
