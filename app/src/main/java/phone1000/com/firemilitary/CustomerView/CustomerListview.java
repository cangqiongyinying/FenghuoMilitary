package phone1000.com.firemilitary.CustomerView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by shiwei on 2016/8/16.
 */
public class CustomerListview extends ListView {
    public CustomerListview(Context context) {
        this(context, null);
    }

    public CustomerListview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, measureSpec);
    }
}
