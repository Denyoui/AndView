package org.andcreator.andview.activity;


import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeConfiguration;

import org.andcreator.andview.R;


public class WelcomeActivity extends com.stephentuso.welcome.WelcomeActivity {

    @Override
    protected WelcomeConfiguration configuration() {
        setTheme(R.style.WelcomeScreenTheme);
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorAccent_Blue)
                .page(new TitlePage(R.drawable.welcome_1,
                        "你以为开源项目是这样维护的？")
                )
                .page(new BasicPage(R.drawable.welcome_2,
                        "开源项目实际上是这样维护的",
                        "(๑*◡*๑)")
                        .background(R.color.colorAccent_Pink)
                )
                .page(new BasicPage(R.drawable.ic_launch_152px,
                        "这里是Open Design",
                        "Start")
                )
                .swipeToDismiss(true)
                .build();
    }
}
