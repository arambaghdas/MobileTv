package iunetworks.mobiletv.presenter;

import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import iunetworks.mobiletv.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import iunetworks.mobiletv.ui.intro.IntroActivity;
import iunetworks.mobiletv.ui.login.SignInActivity;
import iunetworks.mobiletv.ui.SignUpActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class IntroActivityTest {

    @Rule
    public ActivityTestRule<IntroActivity> mActivityRule = new ActivityTestRule<>(IntroActivity.class);

    @Test
    public void testOnClickSignIn(){
        Intents.init();
        onView(withId(R.id.sign_in_button)).perform(click());
        intended(hasComponent(SignInActivity.class.getName()));
    }

    @Test
    public void testOnClickSignUp(){
        onView(withId(R.id.sign_up_button)).perform(click());
        intended(hasComponent(SignUpActivity.class.getName()));
    }


}
