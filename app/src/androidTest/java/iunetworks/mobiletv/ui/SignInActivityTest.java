package iunetworks.mobiletv.ui;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import iunetworks.mobiletv.R;
import iunetworks.mobiletv.ui.login.SignInActivity;
import iunetworks.mobiletv.ui.MainActivity;

import static android.os.SystemClock.sleep;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignInActivityTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void testUserCanInputEmail(){
        onView(withId(R.id.edit_text_email)).perform(typeText("arambaghdas@gmail.com"));
    }

    @Test
    public void testUserCanInputPassword(){
        onView(withId(R.id.edit_text_password)).perform(typeText("Test1234"));
    }

    @Test
    public void testLogInIsEnabled(){
        onView(withId(R.id.edit_text_email)).perform(typeText("arambaghdas@gmail.com"), pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText("Test1234"), pressImeActionButton());
        onView(withId(R.id.button_login)).check(matches(isEnabled()));
    }

    @Test
    public void testLogInIsDisabled(){
        onView(withId(R.id.edit_text_email)).perform(typeText(""), pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText(""), pressImeActionButton());
        onView(withId(R.id.button_login)).check(matches(not(isEnabled())));
    }

    @Test
    public void testLogInDisabledIfPasswordIsEmpty(){
        onView(withId(R.id.edit_text_email)).perform(typeText("arambaghdas@gmail.com"), pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText(""), pressImeActionButton());
        onView(withId(R.id.button_login)).check(matches(not(isEnabled())));
    }

    @Test
    public void testLogInDisabledIfEmailIsEmpty(){
        onView(withId(R.id.edit_text_email)).perform(typeText(""), pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText("Test1234"), pressImeActionButton());
        onView(withId(R.id.button_login)).check(matches(not(isEnabled())));
    }

    @Test
    public void testValidateEmailFail(){
        onView(withId(R.id.edit_text_email)).perform(typeText("ddddd"), pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText("Test1234"), pressImeActionButton());
        sleep(3000);
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.text_view_email_error)).check(matches(withText(R.string.email_validation_error)));
    }

    @Test
    public void testValidateEmailSuccess(){
        onView(withId(R.id.edit_text_email)).perform(typeText("arambaghdas@gmail.com"), pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText("test"), pressImeActionButton());
        sleep(3000);
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.text_view_email_error)).check(matches(withText("")));
    }

    @Test
    public void testValidatePasswordFail(){
        onView(withId(R.id.edit_text_email)).perform(typeText("arambaghdas"), pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText("Test"), pressImeActionButton());
        sleep(3000);
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.text_view_password_error)).check(matches(withText(R.string.password_validation_error)));
    }

    @Test
    public void testValidatePasswordSuccess(){
        onView(withId(R.id.edit_text_email)).perform(typeText("arambaghdas"), pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText("Test1234"), pressImeActionButton());
        sleep(3000);
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.text_view_password_error)).check(matches(withText("")));
    }

    @Test
    public void testValidatePasswordEmailFail(){
        onView(withId(R.id.edit_text_email)).perform(typeText("arambaghdas"), pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText("Test"), pressImeActionButton());
        sleep(3000);
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.text_view_password_error)).check(matches(withText(R.string.password_validation_error)));
        onView(withId(R.id.text_view_email_error)).check(matches(withText(R.string.email_validation_error)));
    }

    @Test
    public void testLogInSuccess(){
        Intents.init();
        onView(withId(R.id.edit_text_email)).perform(typeText("arambaghdas@gmail.com"),  pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText("Test1234"), pressImeActionButton());
        sleep(3000);
        onView(withId(R.id.button_login)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void testLogInFail(){
        onView(withId(R.id.edit_text_email)).perform(typeText("aram@gmail.com"), pressImeActionButton());
        onView(withId(R.id.edit_text_password)).perform(typeText("Test12"), pressImeActionButton());
        sleep(3000);
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.text_view_email_error)).check(matches(withText(R.string.wrong_email)));
        onView(withId(R.id.text_view_password_error)).check(matches(withText(R.string.wrong_password)));
    }

}
