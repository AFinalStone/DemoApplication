package com.shi.androidstudy.widget.swiperefreshview;

/**SwipeRefreshView刷新状态监听接口对象
 * 2016年5月23日 14:27:25
 * **/
public interface OnSwipeRefreshViewListener {
   	/**顶部触发刷新时调用**/
	public void onTopRefreshListener();
	/**底部触发刷新时调用**/
	public void onBottomRefreshListener();
}
