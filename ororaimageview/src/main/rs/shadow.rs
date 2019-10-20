#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(happy.mjstudio.ororaimageview)

rs_allocation inImage;
int inWidth;
int inHeight;



static bool is_transparent(uchar4 in){
    return in.a == 0;
}


uchar4 RS_KERNEL add_shadow (uint32_t x, uint32_t y) {
    uchar4 out = rsGetElementAt_uchar4(inImage, x, y);


    return out;
}



