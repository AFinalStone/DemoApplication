package com.example.youkumenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends Activity implements OnClickListener{
	//一级菜单中的home按钮
	private ImageView home_Iv;
	//二级菜单中的Menu按钮
	private ImageView menu_Iv;
	//用于判断二级菜单的显示状况，true为显示，false为隐藏
	private boolean level2ListPlay = true;
	//用于判断二级菜单的显示状况，true为显示，false为隐藏
	private boolean level3ListPlay = true;
	//二级和三级菜单
	private RelativeLayout level2_Rl,level3_Rl;
	//用于记录有多少个动画在执行
	private int annimationCount = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
	//初始化组件
	private void initView() {
		home_Iv = (ImageView) findViewById(R.id.home_Iv);
		home_Iv.setOnClickListener(this);

		level2_Rl = (RelativeLayout) findViewById(R.id.level2_Rl);
		level3_Rl = (RelativeLayout) findViewById(R.id.level3_Rl);

		menu_Iv = (ImageView) findViewById(R.id.menu_Iv);
		menu_Iv.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.home_Iv:		//点击home按钮的逻辑代码
				clickHomeIv();
				break;
			case R.id.menu_Iv:
				clickMenuIv();		//点击二级菜单中的menu按钮的逻辑代码
				break;
			default:
				break;
		}
	}
	//点击二级菜单中的menu按钮的逻辑代码
	private void clickMenuIv() {
		//当点击的时候就可以进行判断，只要annimationCount值大于0，说明还有动画在执行
		if (annimationCount > 0) {
			return ;
		}
		//分情况考虑
		//1.三级显示的时候，就将三级菜单隐藏
		if (level3ListPlay) {
			hideMenu(level3_Rl,0);
			level3ListPlay = false;
			return;
		}
		//2.三级隐藏的时候，就将三级菜单显示
		if (!level3ListPlay) {
			showMenu(level3_Rl);
			level3ListPlay = true;
			return;
		}
	}
	//点击一级菜单中的home按钮的逻辑代码
	private void clickHomeIv() {
		//当点击的时候就可以进行判断，只要annimationCount值大于0，说明还有动画在执行
		if (annimationCount > 0) {
			return ;
		}
		//分情况考虑
		//1.二级、三级菜单都显示，就将二、三级菜单隐藏掉
		if (level2ListPlay && level3ListPlay) {
			//将二三级菜单隐藏，并改变标记
			hideMenu(level2_Rl,300);
			hideMenu(level3_Rl,500);
			level2ListPlay = false;
			level3ListPlay = false;
			return;
		}
		//2.二、三级菜单都不显示的时候，就将二级菜单显示
		if (!level2ListPlay && !level3ListPlay) {
			showMenu(level2_Rl);
			level2ListPlay = true;
			return;
		}
		//3.二级菜单显示且三级菜单不显示的时候，就将二级菜单隐藏
		if (level2ListPlay && !level3ListPlay) {
			hideMenu(level2_Rl,0);
			level2ListPlay = false;
			return;
		}

	}
	/**
	 * 显示菜单
	 * @param view  要显示的菜单
	 */
	private void showMenu(RelativeLayout view) {
//		view.setVisibility(View.VISIBLE);
		//如果要显示菜单，那么就将相应的控件设为有焦点
		//获取父容器中有几个子控件
		int childCount = view.getChildCount();
		for (int i = 0; i < childCount; i++) {
			view.getChildAt(i).setEnabled(true);
		}
		//旋转动画
		RotateAnimation animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setDuration(500);		//设置动画持续的时间
		animation.setFillAfter(true); //动画停留在动画结束的位置
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				//动画开始的时候回调
//				menu_Iv.setOnClickListener(null);
//				home_Iv.setOnClickListener(null);
				annimationCount ++;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				//动画执行过程调用
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//动画结束的时候调用
//				menu_Iv.setOnClickListener(MainActivity.this);
//				home_Iv.setOnClickListener(MainActivity.this);
				annimationCount --;
			}
		});
	}
	/**
	 * 隐藏菜单
	 * @param view  要隐藏的菜单 ,startOffset 动画延迟执行的时间
	 */
	private void hideMenu(RelativeLayout view,long startOffset) {
//		view.setVisibility(View.GONE);
		//如果要隐藏菜单，那么就将相应的控件设为没有焦点
		//获取父容器中有几个子控件
		int childCount = view.getChildCount();
		for (int i = 0; i < childCount; i++) {
			view.getChildAt(i).setEnabled(false);
		}
		/**
		 * 旋转动画
		 * RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue)
		 * fromDegrees 开始旋转角度
		 * toDegrees 旋转的结束角度
		 * pivotXType X轴 参照物 （X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF）
		 * pivotXValue x轴 旋转的参考点（x坐标的伸缩值）
		 * pivotYType Y轴 参照物 （Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF）
		 * pivotYValue Y轴 旋转的参考点 (（Y坐标的伸缩值） )
		 */
		RotateAnimation animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setDuration(500);
		animation.setFillAfter(true); //动画停留在动画结束的位置
		animation.setStartOffset(startOffset);		//设置动画的延迟执行
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
//				menu_Iv.setOnClickListener(null);
//				home_Iv.setOnClickListener(null);
				annimationCount ++;
			}
			@Override
			public void onAnimationRepeat(Animation animation) {

			}
			@Override
			public void onAnimationEnd(Animation animation) {
//				menu_Iv.setOnClickListener(MainActivity.this);
//				home_Iv.setOnClickListener(MainActivity.this);
				annimationCount --;
			}
		});
	}
}
