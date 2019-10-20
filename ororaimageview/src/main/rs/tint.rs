#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(happy.mjstudio.ororaimageview)

int tint_r = 0;
int tint_g = 0;
int tint_b = 0;

uchar4 RS_KERNEL tint (uchar4 in,int x, int y) {

    uchar4 out = in;

    out.r = tint_r;
    out.g = tint_g;
    out.b = tint_b;

    if(in.a > 0){
        out.a = out.a * 0.4;
    }else{
        out.rgb = 0;
        out.a = 0;
    }

    //rsDebug("alpha",in.a,out.a,x,y);

    return out;
}
