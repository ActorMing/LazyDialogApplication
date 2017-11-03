# LazyDialogApplication
lazyDialog工具类
   方便在android中进行显示自定义的布局样式,提高开发效率.
   ===========

配置:
    Step 1. Add the JitPack repository to your build file
    Add it in your root build.gradle at the end of repositories:

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


    Step 2. Add the dependency
    dependencies {
	        compile 'com.github.ActorMing:LazyDialogApplication:v1.0.0'
	}


    
使用方式:

         LazyDialog.init()
                        .setLayoutId(R.layout.share_layout)
                        .setConvertListener(new ConvertViewListener() {
                            @Override
                            protected void convertView(LazyViewHolder lazyViewHolder, final BaseLazyDialog                  baseLazyDialog) {
                                lazyViewHolder.getView(R.id.iv_share_qq).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        baseLazyDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "app will start qq", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setWidth(0) // 设置宽度 0代表屏幕宽度,-1代表内容包裹
                        .setMargin(0) // 边距
                        .setShowBottom(true) // 是否显示在底部
                        .setOutCancel(true) // 点击外部是否可以取消
                        .show(getSupportFragmentManager()); // 需要传入 FragmentManager对象
			
			
			
			
			
			源项目地址https://github.com/Othershe/NiceDialog
