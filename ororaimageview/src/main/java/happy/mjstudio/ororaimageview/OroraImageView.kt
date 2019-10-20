package happy.mjstudio.ororaimageview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.AttributeSet
import android.widget.ImageView
import androidx.core.math.MathUtils

/**
 * Created by mj on 20, October, 2019
 */

class OroraImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ImageView(context, attrs) {

    private fun Int.toPixel() = context.resources.displayMetrics.density * this

    private lateinit var rs: RenderScript
    private lateinit var scriptTint: ScriptC_tint
    private lateinit var scriptBlur: ScriptIntrinsicBlur
    private lateinit var element: Element

    var blurRadius: Float = 15f
        set(value) {
            invalidate()
            field = MathUtils.clamp(value, 0f, 25f)
        }

    var shadowColor: Int = Color.YELLOW
        set(value) {
            invalidate()
            field = value
        }

    var shadowOffsetX: Float = 4.toFloat()
        set(value) {
            invalidate()
            field = value
        }
    var shadowOffsetY: Float = 4.toFloat()
        set(value) {
            invalidate()
            field = value
        }

//    var clipShadow : Boolean = true
//        set(value) {
//            invalidate()
//            field = value
//        }

    init {

        attrs?.let { attrs ->
            context.obtainStyledAttributes(attrs,R.styleable.OroraImageView,0,0).apply {
                blurRadius = this.getFloat(R.styleable.OroraImageView_orora_blur_radius,blurRadius)
                shadowColor = this.getColor(R.styleable.OroraImageView_orora_shadow_color,shadowColor)
                shadowOffsetX = this.getDimension(R.styleable.OroraImageView_orora_shadow_offset_x,shadowOffsetX)
                shadowOffsetY = this.getDimension(R.styleable.OroraImageView_orora_shadow_offset_y,shadowOffsetY)
//                clipShadow = this.getBoolean(R.styleable.OroraImageView_orora_clip_shadow,clipShadow)
            }.recycle()
        }



    }

    override fun onAttachedToWindow() {

        if (!isInEditMode) {
            rs = RenderScript.create(context)
            element = Element.U8_4(rs)
            scriptTint = ScriptC_tint(rs)
            scriptBlur = ScriptIntrinsicBlur.create(rs, element)
        }

        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {

        if (!isInEditMode) {
            scriptTint.destroy()
            scriptBlur.destroy()
            rs.destroy()
        }

        super.onDetachedFromWindow()
    }


    private fun getOroraBitmap(): Bitmap {
        val bitmap = getBitmapFromDrawable()

        val inputAllocation = Allocation.createFromBitmap(rs, bitmap)
        val middleAllocation = Allocation.createTyped(rs, inputAllocation.type)
        val outputAllocation = Allocation.createTyped(rs, inputAllocation.type)


        scriptTint._tint_r = Color.red(shadowColor)
        scriptTint._tint_g = Color.green(shadowColor)
        scriptTint._tint_b = Color.blue(shadowColor)
        scriptTint.forEach_tint(inputAllocation, middleAllocation)


        middleAllocation.syncAll(Allocation.USAGE_SCRIPT)

        if (blurRadius > 0) {
            scriptBlur.setRadius(blurRadius)
            scriptBlur.setInput(middleAllocation)

            scriptBlur.forEach(outputAllocation)
            outputAllocation.copyTo(bitmap)
        } else {
            middleAllocation.copyTo(bitmap)
        }


        inputAllocation.destroy()
        middleAllocation.destroy()
        outputAllocation.destroy()

        return bitmap
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (!isInEditMode && canvas != null) {
            if (drawable != null) {
                val bounds = drawable.copyBounds()
                val ororaBitmap = getOroraBitmap()

//                if(clipShadow) {
//                    canvas.save()
//
//
//                    val newRect = canvas.clipBounds
//                    newRect.inset(-shadowOffsetX.toInt() * 2,-shadowOffsetY.toInt() * 2)
//                    Log.e("rect",newRect.toString())
//                    canvas.clipRect(newRect)
//
//                    canvas.drawBitmap(
//                        ororaBitmap,
//                        bounds.left.toFloat() + shadowOffsetX,
//                        bounds.top.toFloat() + shadowOffsetY,
//                        null
//                    )
//
//                    canvas.restore()
//                }else {

                    canvas.drawBitmap(
                        ororaBitmap,
                        bounds.left.toFloat() + shadowOffsetX + paddingLeft,
                        bounds.top.toFloat() + shadowOffsetY + paddingTop,
                        null
                    )
//                }
            }
//            drawable?.let { drawable ->
//                val bounds = drawable.copyBounds()
//                shadowBitmap?.let {
//                    canvas.save()
//
//                    if (!clipShadow) {
//                        canvas.getClipBounds(rect)
//                        rect.inset(-2 * getBlurRadius().toInt(), -2 * getBlurRadius().toInt())
//                        if (forceClip) {
//                            canvas.clipRect(rect)
//                        } else {
//                            canvas.save()
//                            canvas.clipRect(rect)
//                        }
//                        canvas.drawBitmap(
//                            it,
//                            bounds.left.toFloat() - getBlurRadius(),
//                            bounds.top - getBlurRadius() / 2f,
//                            null
//                        )
//                    }
//
//                    canvas.restore()
//                }
//            }
        }
        super.onDraw(canvas)
    }

    private fun getBitmapFromDrawable(): Bitmap {

        val drawable = drawable

        val width = width + 2 * blurRadius.toInt()
        val height = height + 2 * blurRadius.toInt()

        val bitmap = if (width <= 0 || height <= 0) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap)

        val imageMatrix = imageMatrix
//        canvas.translate(paddingLeft + blurRadius, paddingTop + blurRadius)
        if (imageMatrix != null) {
            canvas.concat(imageMatrix)
        }
        drawable.draw(canvas)

        return bitmap
    }
}