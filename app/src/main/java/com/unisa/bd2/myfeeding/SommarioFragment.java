package com.unisa.bd2.myfeeding;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SommarioFragment extends Fragment{


    ImageView imageProductView;
    ImageView nutriScoreView;
    TextView nameprductView;
    TextView genericNameView;
    TextView barcodeView;
    TextView quantityView;
    TextView brandsView;

    View view;
    Prodotto prod;
    Animation animZoomIn;
    private Animator mCurrentAnimatorEffect;


    public SommarioFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sommario_fragment, container, false);

        imageProductView = view.findViewById(R.id.imgProduct);
        nutriScoreView = view.findViewById(R.id.nutriScore);
        nameprductView = view.findViewById(R.id.titleProduct);
        genericNameView = view.findViewById(R.id.genericNameProduct);
        barcodeView = view.findViewById(R.id.valueBarcode);
        quantityView = view.findViewById(R.id.valueQuantity);
        brandsView = view.findViewById(R.id.valueBrands);

        prod = getArguments().getParcelable("prodotto");

        System.out.println("SOMMARIO FRAGMENTTT prod "+prod.toString());



        imageProductView.setImageBitmap(prod.getImageProduct());

        /*
        // load the animation
        animZoomIn = AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in);

        // set animation listener
        animZoomIn.setAnimationListener(this);
        */

        /*
        imageProductView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animZoomIn);
            }
        });
        */

        /*
        final View thumbImageView = view.findViewById(R.id.imageProduct);
        thumbImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //zoomImageFromThumb(thumbImageView, R.drawable.cat);
            }
        });
        */




        System.out.println("NUTRITIONSCORE "+prod.getNutritionScore());

        String value = prod.getNutritionScore();

        if(prod.getNutritionScore()==null){
            nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.image_not_found));
        }

        else {
            if(value.equals("a")){
                nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.nutriscore_a));
            }
            else if(value.equals("b")){
                nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.nutriscore_b));
            }
            else if(value.equals("c")){
                nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.nutriscore_c));
            }
            else if(value.equals("d")){
                nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.nutriscore_d));
            }
            else if(value.equals("e")) {
                nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.nutriscore_e));
            }
        }

        nameprductView.setText(prod.getProductName());
        genericNameView.setText(prod.getGenericName());
        barcodeView.setText(String.valueOf(prod.getBarcode()));
        quantityView.setText(prod.getQuantity());
        brandsView.setText(prod.getBrand());


        return view;

    }



    /*
    private void zoomImageFromThumb(final View thumbView, int imageResId) {
        if (mCurrentAnimatorEffect != null) {
            mCurrentAnimatorEffect.cancel();
        }

        final ImageView expandedImageView = (ImageView) view.findViewById(
                R.id.expanded_image);
        expandedImageView.setImageResource(imageResId);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDurationEffect);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimatorEffect = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimatorEffect = null;
            }
        });
        set.start();
        mCurrentAnimatorEffect = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimatorEffect != null) {
                    mCurrentAnimatorEffect.cancel();
                }

                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDurationEffect);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimatorEffect = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimatorEffect = null;
                    }
                });
                set.start();
                mCurrentAnimatorEffect = set;
            }
        });
    }
    */



}
