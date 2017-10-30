package com.project.espressotest.espresso;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void clearList(){
        Context context = InstrumentationRegistry.getTargetContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonSharedPreference.NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    @Test
    public void nothingTestCase() {
        onView(withId(R.id.editTExtName)).perform(clearText());
        onView(withId(R.id.editTextAge)).perform(clearText());
        onView(withId(R.id.buttonAdded)).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));

    }

    @Test
    public void onlyAgeTestCase(){
        onView(withId(R.id.editTExtName)).perform(clearText());
        onView(withId(R.id.editTextAge)).perform(replaceText("20"), closeSoftKeyboard());
        onView(withId(R.id.buttonAdded)).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
    }

    @Test
    public void emptyListTestCase(){
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.textNotFound)).check(matches(isDisplayed()));
    }

    @Test
    public void onlyNameTestCase(){
        onView(withId(R.id.editTExtName)).perform(replaceText("Ying"), closeSoftKeyboard());
        onView(withId(R.id.editTextAge)).perform(clearText());
        onView(withId(R.id.buttonAdded)).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
    }

    @Test
    public void addUserYingTestCase(){
        addUser("Ying", "20");
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.list)).check(matches(atPosition(0, hasDescendant(withText("Ying")))));
        onView(withId(R.id.list)).check(matches(atPosition(0, hasDescendant(withText("20")))));

    }

    @Test
    public void addUserLadaratTestCase(){
        addUser("Ying", "20");
        addUser("Ladarat", "20");
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.list)).check(matches(atPosition(1, hasDescendant(withText("Ladarat")))));
        onView(withId(R.id.list)).check(matches(atPosition(1, hasDescendant(withText("20")))));
    }

    @Test
    public void addUserSomkaitTestCase(){
        addUser("Ying", "20");
        addUser("Ladarat", "20");
        addUser("Somkait", "80");
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.list)).check(matches(atPosition(2, hasDescendant(withText("Somkait")))));
        onView(withId(R.id.list)).check(matches(atPosition(2, hasDescendant(withText("80")))));
    }

    @Test
    public void addUserPrayochTestCase(){
        addUser("Ying", "20");
        addUser("Ladarat", "20");
        addUser("Somkait", "80");
        addUser("Prayoch", "60");
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.list)).check(matches(atPosition(3, hasDescendant(withText("Prayoch")))));
        onView(withId(R.id.list)).check(matches(atPosition(3, hasDescendant(withText("60")))));
    }

    @Test
    public void addAnotherPrayochTestCase(){
        addUser("Ying", "20");
        addUser("Ladarat", "20");
        addUser("Somkait", "80");
        addUser("Prayoch", "60");
        addUser("Prayoch", "50");
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.list)).check(matches(atPosition(4, hasDescendant(withText("Prayoch")))));
        onView(withId(R.id.list)).check(matches(atPosition(4, hasDescendant(withText("50")))));
    }


    private void addUser(String name, String age){
        onView(withId(R.id.editTExtName)).perform(replaceText(name), closeSoftKeyboard());
        onView(withId(R.id.editTextAge)).perform(replaceText(age), closeSoftKeyboard());
        onView(withId(R.id.buttonAdded)).perform(click());
    }



    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
