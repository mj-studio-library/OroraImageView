# OroraImageView
Handy Drop Shadow image in ImageView


----
## Idea from
[Medium](https://medium.com/@qhutch/how-i-extended-imageview-to-include-elevation-shadow-2a41151a4156)

[ElevationImageView](https://github.com/qhutch/ElevationImageView)

## Sample

<img src="https://github.com/mym0404/OroraImageView/blob/master/sample.gif" width=250>


## How?

- RenderScript


## Usage

### xml

```xml
<happy.mjstudio.ororaimageview.OroraImageView
    app:orora_blur_radius="25.0"
    app:orora_shadow_color="@color/colorPrimary"
    app:orora_shadow_offset_x="1dp"
    app:orora_shadow_offset_y="1dp"
    android:scaleType="centerInside"
    android:src="@drawable/mjstudio"
    android:id="@+id/orora"
    ...
 />
```

### code

```kotlin
orora.shadowColor = Color.BLACK

orora.blurRadius = 25f

orora.shadowOffsetX = 4f

orora.shadowOffsetY = 4f

```

----

## Download

- MinSDK = 14

### Project-level build.gradle

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}


defaultConfig {
    ...

    renderscriptTargetApi 18
    renderscriptSupportModeEnabled false
}

```

### Module-level build.gradle

```gradle
implementation 'com.github.mym0404:OroraImageView:1.1'
```

----
