<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FFF"
    tools:context="com.example.hp.jarappliation.JarDetailActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/ll_header"
        android:paddingTop="10dp"
        android:paddingBottom="10dp"
        android:background="@drawable/bg_bottom_shaddow"
        android:orientation="vertical">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/tv_mj1"
            android:layout_gravity="center_horizontal"
            android:gravity="center"
            android:textColor="@color/colorPrimary"
            android:text="KNOW WHAT DAD \nREALLY WANTS"/>
    </LinearLayout>
    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/rl_1"
        android:orientation="horizontal"
        android:layout_marginTop="10dp"
        android:layout_below="@id/ll_header"
        android:paddingBottom="15dp">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/tv_fam"
            android:text="DAD"
            android:layout_centerHorizontal="true"
            android:textColor="@color/theme_purple"
            android:textSize="21sp"/>
    </RelativeLayout>
    <LinearLayout
        android:id="@+id/ll_bottom"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:layout_alignParentBottom="true"
        android:paddingTop="20dp">
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <TextView
                android:id="@+id/tv_added_to_fav"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                