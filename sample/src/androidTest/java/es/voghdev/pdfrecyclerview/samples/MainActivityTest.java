package es.voghdev.pdfrecyclerview.samples;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class) @LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    @Test
    public void shouldDisplayARecyclerViewOnStart() {
        startActivity();

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    private MainActivity startActivity() {
        return activityRule.launchActivity(null);
    }
}