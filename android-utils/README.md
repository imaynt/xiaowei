# Android - Tools Index
This is an Android doc index for Android developers

#some open libraries
ImageLoader加载本地视频方式

加载http图片
String imageUri = "http://site.com/image.png";//from web


加载sd卡和本地手机内存图片
String imageUri = "file:///mnt/sdcard/image.png";//from SD card  内存图片


加载content provider  
String imageUri = "content://media/external/audio/albumart/13";//from content provider

加载assets 图片
String imageUri = "assets://image.png";//from assets

加载drawable图片
String imageUri = "drawable://" + R.drawable.image;//from drawables (only images,non-9patch);
