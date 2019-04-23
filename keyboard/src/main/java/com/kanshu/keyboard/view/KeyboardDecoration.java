package com.kanshu.keyboard.view;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 需要注意的是,getItemOffsets是针对每个Item都会走一次，也就是说每个Item的outRect都可以不同，但是onDraw和onDrawOver所整个ItemDecoration只执行一次的，
 * 并不是针对Item的，所以我们需要在onDraw和onDrawOver中绘图时，一次性将所有Item的ItemDecoration绘制完成。
 * 从上面也可以看出，这里在onDraw函数中绘图时，通过for循环对每一个item画上一个绿色圆。
 */
public class KeyboardDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "LinearItemDecoration";

    /**
     * @param outRect 这个是最难理解的部分，outRect就是表示在item的上下左右所撑开的距离，后面详细讲解。
     * @param view    是指当前Item的View对象
     * @param parent  是指RecyclerView本身
     * @param state   通过State可以获取当前RecyclerView的状态，也可以通过State在RecyclerView各组件间传递参数
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //获取item(按键)的数量
        int childCount = parent.getChildCount();

        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        if (layoutManager != null) {
            //获取列数
            int spanCount = layoutManager.getSpanCount();
            //获取当前按钮的位置
            int pos = parent.getChildAdapterPosition(view);


            if (pos % spanCount == 0) {
                //该view在第一列
                outRect.right = 5;
                outRect.left = 10;
            } else if (pos % spanCount == spanCount - 1) {
                //该view在最后一列
                //该view在第一列
                outRect.left = 5;
                outRect.right = 10;
            } else {
                outRect.left = 5;
                outRect.right = 5;
            }
        } else {
            outRect.bottom = 10; //单位是px
            outRect.right = 10;
        }


    }

}
