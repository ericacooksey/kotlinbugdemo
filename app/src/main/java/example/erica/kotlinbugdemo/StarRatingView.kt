package example.erica.kotlinbugdemo

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout

class StarRatingView : LinearLayout {
    private val TAG = StarRatingView::class.java.simpleName
    private var imgStarOpen: Drawable
    private var imgStarFilled: Drawable
    private var numStars: Int
    private var rating: Int

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // Set up the layout
        this.orientation = LinearLayout.HORIZONTAL
        this.gravity = Gravity.CENTER_HORIZONTAL
        // Obtain the star drawables
        imgStarOpen = context.getDrawable(R.drawable.ic_star_open)
        imgStarFilled = context.getDrawable(R.drawable.ic_star_filled)
        // Set the attributes styled in XML
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StarRatingView)
        numStars = typedArray.getInt(R.styleable.StarRatingView_numStars, 5)
        rating = typedArray.getInt(R.styleable.StarRatingView_rating, 0)
        typedArray.recycle()

        // Set up the view
        val dimen = context.resources.getDimensionPixelSize(R.dimen.star_rating_dimen)
        val margin = context.resources.getDimensionPixelSize(R.dimen.star_rating_margin)
        // Iterate over the number of stars, adding each one to the layout
        // Use proper for loop instead of while
        for (i in 0 until numStars) {
            val starImageView: ImageView = ImageView(context, null, R.style.StarRating_Star)
            // Use filled stars if rating is initialized
            if (i < rating) {
                starImageView.background = imgStarFilled
            } else {
                starImageView.background = imgStarOpen
            }
            // Set a click listener so that tapping the star sets it as the rating
            starImageView.setOnClickListener {
                // Now we don't need temporary variable, because i now immutable
                setRating(i + 1) // index + 1 is the rating
            }
            // Set the margin in the layout params
            var layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(dimen, dimen)
            if (i < numStars - 1) {
                layoutParams.rightMargin = margin
            }
            // Add the star as a child view
            addView(starImageView, layoutParams)
        }
    }

    /**
     * Exposed method to set the rating
     */
    fun setRating(newRating: Int) {
        this.rating = newRating
        var i: Int = 0
        while (i < numStars) {
            var imageView: ImageView = this.getChildAt(i) as ImageView
            if (i < rating) {
                imageView.background = imgStarFilled
            } else {
                imageView.background = imgStarOpen
            }
            i++
        }
    }

    /**
     * @return The user-defined rating
     */
    fun getRating(): Int {
        return this.rating
    }
}
