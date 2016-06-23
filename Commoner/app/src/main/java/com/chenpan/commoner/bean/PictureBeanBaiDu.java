package com.chenpan.commoner.bean;

import android.graphics.Picture;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */

public class PictureBeanBaiDu implements PictureBean,Serializable{

    private String downloadUrl;
    private String imageUrl;
    private int imageWidth;
    private int imageHeight;
    private String thumbnailUrl;
    private int thumbnailWidth;
    private int thumbnailHeight;
    private String thumbnailLargeUrl;
    private int thumbnailLargeWidth;
    private int thumbnailLargeHeight;
    private String thumbnailLargeTnUrl;
    private int thumbnailLargeTnWidth;
    private int thumbnailLargeTnHeight;


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public String getThumbnailLargeUrl() {
        return thumbnailLargeUrl;
    }

    public void setThumbnailLargeUrl(String thumbnailLargeUrl) {
        this.thumbnailLargeUrl = thumbnailLargeUrl;
    }

    public int getThumbnailLargeWidth() {
        return thumbnailLargeWidth;
    }

    public void setThumbnailLargeWidth(int thumbnailLargeWidth) {
        this.thumbnailLargeWidth = thumbnailLargeWidth;
    }

    public int getThumbnailLargeHeight() {
        return thumbnailLargeHeight;
    }

    public void setThumbnailLargeHeight(int thumbnailLargeHeight) {
        this.thumbnailLargeHeight = thumbnailLargeHeight;
    }

    public String getThumbnailLargeTnUrl() {
        return thumbnailLargeTnUrl;
    }

    public void setThumbnailLargeTnUrl(String thumbnailLargeTnUrl) {
        this.thumbnailLargeTnUrl = thumbnailLargeTnUrl;
    }

    public int getThumbnailLargeTnWidth() {
        return thumbnailLargeTnWidth;
    }

    public void setThumbnailLargeTnWidth(int thumbnailLargeTnWidth) {
        this.thumbnailLargeTnWidth = thumbnailLargeTnWidth;
    }

    public int getThumbnailLargeTnHeight() {
        return thumbnailLargeTnHeight;
    }

    public void setThumbnailLargeTnHeight(int thumbnailLargeTnHeight) {
        this.thumbnailLargeTnHeight = thumbnailLargeTnHeight;
    }
}
