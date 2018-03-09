package smart.sftinyservice.gson;

/**
 * Created by jasonsam on 2018/3/7.
 */

public class FaceBox {
    public float left;
    public float top;
    public float right;
    public float bottom;
    public float width;
    public float height;
    public float centerX;
    public float centerY;

    public FaceBox(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.width = right - left;
        this.height = bottom - top;
        this.centerX = (right + left) / 2;
        this.centerY = (top + bottom) / 2;
    }



}
