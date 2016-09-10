package phone1000.com.firemilitary.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by my on 2016/9/10.
 */
public class NoScrollExpandableListView extends ExpandableListView {
    public NoScrollExpandableListView(Context context) {
        this(context,null);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
