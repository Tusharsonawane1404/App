package edu.stevens.cs522.chatserver.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import edu.stevens.cs522.base.DateUtils;

/**
 * Created by dduggan.
 */

/*
 * TODO annotate as entity object
 *
 * Since foreign keys reference the name field, we need to define a unique index on that.
 */
@Entity(indices = {@Index(value = "name", unique = true)})
public class Peer implements Parcelable {

    // TODO primary key
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public Date timestamp;

    // Where we heard from them
    public Double latitude;

    public Double longitude;

    @Override
    public String toString() {
        return name;
    }

    public Peer() {
    }

    public Peer(Parcel in) {
        // TODO
		id = in.readLong();
        name = in.readString();
        timestamp = DateUtils.readDate(in);
        latitude = in.readDouble();
        longitude = in.readDouble();

    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // TODO
		out.writeLong(id);
        out.writeString(name);
        DateUtils.writeDate(out, timestamp);
        out.writeDouble(latitude);
        out.writeDouble(longitude);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Peer> CREATOR = new Creator<Peer>() {

        @Override
        public Peer createFromParcel(Parcel source) {
            // TODO
            //return null;

            return new Peer(source);
        }

        @Override
        public Peer[] newArray(int size) {
            // TODO
            //return null;

            return new Peer[size];
        }

    };
}
