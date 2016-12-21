package com.iste776.jpavelw.map.DAO;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by jpavelw on 11/17/16.
 */

@Entity
public class Category {
    @Id
    private long ID;
    @NotNull
    private String name;
    @NotNull
    private Date date;

    private Category(){ this.date = new Date(); }

    public Category(@NotNull long ID) {
        this();
        this.ID = ID;
    }

    public Category(@NotNull String name) {
        this();
        this.name = name;
    }

    @Generated(hash = 198042781)
    public Category(long ID, @NotNull String name, @NotNull Date date) {
        this.ID = ID;
        this.name = name;
        this.date = date;
    }

    public long getID() { return ID; }
    public void setID(long ID) { this.ID = ID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
