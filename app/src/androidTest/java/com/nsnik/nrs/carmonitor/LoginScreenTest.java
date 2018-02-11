package com.nsnik.nrs.carmonitor;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nsnik.nrs.carmonitor.views.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openLibrariesListTest() {
        onView(withId(R.id.formName)).perform(typeText("nikhil"));
        onView(withId(R.id.formPhone)).perform(typeText("8013410076"));
        onView(withId(R.id.formCarNo)).perform(typeText("wb1954"));
        onView(withId(R.id.fromSubmit)).perform(click());
    }

}
