package com.lmy.hwahae.ui.views

import android.view.View
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.lmy.hwahae.R
import com.lmy.hwahae.ui.adpaters.IndexViewAdapter
import kotlinx.coroutines.delay
import org.hamcrest.Matcher
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class IndexViewActivityTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(IndexViewActivity::class.java)

    /**
     * Test View's Visibility
     */
    @Test
    fun test_isIndexViewActivityInView() {
        onView(withId(R.id.cl_index_view_main)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_navigation_bar() {
        onView(withId(R.id.cl_navigation_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_status_bar() {
        onView(withId(R.id.cl_status_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_time() {
        onView(withId(R.id.tv_time)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_cellular_icon() {
        onView(withId(R.id.iv_cellular)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_wifi_icon() {
        onView(withId(R.id.iv_wifi)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_battery_icon() {
        onView(withId(R.id.iv_battery)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_search_field() {
        onView(withId(R.id.cl_search_field)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_search_view() {
        onView(withId(R.id.sv_search)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_progress_bar() {
        onView(withId(R.id.pb_loading_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun test_isTimeTextDisplayed() {
        onView(withId(R.id.tv_time)).check(matches(withText(R.string.tv_time_text)))
    }

    @Test
    fun test_visibility_recycler_view() {
        onView(withId(R.id.rv_product)).check(matches(isDisplayed()))
    }

    /**
     * Test View's Action Event
     */
    @Test
    fun test_selectSpinnerItem_isItem_changed() {

        // Select the skin type and verify the item is changed
        onView(withId(R.id.sp_skin_type)).perform(click())
        onData(anything()).atPosition(0).perform(click()) // "모든 피부 타입"
        onView(withId(R.id.sp_skin_type)).check(matches(withSpinnerText(containsString(SpinnerData.getAllSkinTypes()[0]))))

        onView(withId(R.id.sp_skin_type)).perform(click())
        onData(anything()).atPosition(1).perform(click()) // "Oily"
        onView(withId(R.id.sp_skin_type)).check(matches(withSpinnerText(containsString(SpinnerData.getAllSkinTypes()[1]))))

        onView(withId(R.id.sp_skin_type)).perform(click())
        onData(anything()).atPosition(2).perform(click()) // "Dry"
        onView(withId(R.id.sp_skin_type)).check(matches(withSpinnerText(containsString(SpinnerData.getAllSkinTypes()[2]))))

        onView(withId(R.id.sp_skin_type)).perform(click())
        onData(anything()).atPosition(3).perform(click()) // "Sensitive"
        onView(withId(R.id.sp_skin_type)).check(matches(withSpinnerText(containsString(SpinnerData.getAllSkinTypes()[3]))))
    }

    @Test
    fun test_typeKeyword_inSearchView_isText_displayed() {

        // Given
        val testString = "User's Keyword"

        // Type the user's keyword and verify it's displayed
        onView(withId(R.id.sv_search)).perform(click())
        onView(withId(R.id.search_src_text)).perform(typeText(testString))
        onView(withId(R.id.search_src_text)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isDetailViewDialog_visible() {

        // Select the item of recyclerView and verify DetailViewDialog is displayed
        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.rv_product)).perform(actionOnItemAtPosition<IndexViewAdapter.ItemViewHolder>(0, click())) // IndexViewDialog's RecyclerView
        onView(isRoot()).perform(waitFor(3000))
        onView(withId(R.id.rl_main)).check(matches(isDisplayed())) // DetailViewDialog's RelativeLayout
        onView(withId(R.id.iv_product_image)).check(matches(isDisplayed())) // DetailViewDialog's ImageView, product image
        onView(withId(R.id.tv_product_title)).check(matches(isDisplayed())) // DetailViewDialog's TextView, product title
        onView(withId(R.id.tv_product_price)).check(matches(isDisplayed())) // DetailViewDialog's TextView, product price
        onView(withId(R.id.tv_product_description)).check(matches(isDisplayed())) // DetailViewDialog's TextView, product description
    }

    @Test
    fun test_pressBack_isDetailViewDialog_invisible() {

        // Press the back button of DetailViewDialog and verify DetailViewDialog is dismissed
        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.rv_product)).perform(actionOnItemAtPosition<IndexViewAdapter.ItemViewHolder>(0, click())) // IndexViewDialog's RecyclerView
        onView(isRoot()).perform(waitFor(3000))
        onView(withId(R.id.rl_main)).check(matches(isDisplayed())) // DetailViewDialog's RelativeLayout
        pressBack()
        onView(withId(R.id.rv_product)).check(matches(isDisplayed()))
    }

    fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(delay)
            }

            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }
        }
    }
}
