package com.unisa.bd2.myfeeding;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URL;

public class IngredientiFragment extends Fragment {

    View view;
    ImageView ingredientsImage;
    TextView ingredientsList;
    TextView sostanzeList;
    TextView additiviList;
    TextView olioList;
    TextView tracesList;

    LinearLayout fisrtLinear;
    LinearLayout secondLinear;
    LinearLayout thirdLinear;
    LinearLayout fourLinear;
    LinearLayout fiveLinear;


    Prodotto prod;
    Drawable icon;
    private Animator mCurrentAnimatorEffect;
    private int mShortAnimationDurationEffect;
    Bitmap image;


    public IngredientiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ingredienti_fragment, container, false);

        ingredientsImage = view.findViewById(R.id.ingredientsImage);
        ingredientsList = view.findViewById(R.id.list_Ingredients);
        sostanzeList = view.findViewById(R.id.list_Sostanze);
        additiviList = view.findViewById(R.id.list_Additivi);
        olioList = view.findViewById(R.id.list_Olio);
        tracesList = view.findViewById(R.id.traces_List);

        fisrtLinear = view.findViewById(R.id.firsLinear);
        secondLinear = view.findViewById(R.id.secondLinear);
        thirdLinear = view.findViewById(R.id.thirdLinear);
        fourLinear = view.findViewById(R.id.fourLinear);
        fiveLinear = view.findViewById(R.id.fiveLinear);


        prod = getArguments().getParcelable("prodotto");

        System.out.println("INGREDIENTI FRAGMENTTT prod " + prod.toString());


        try {
            image = downloadImage(prod.getIngredientsImageUrl());
            ingredientsImage.setImageBitmap(image);
        } catch (Exception e) {
            icon = getResources().getDrawable(R.drawable.image_not_found);
            Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
            image = ((BitmapDrawable) icon).getBitmap();
            ingredientsImage.setImageBitmap(((BitmapDrawable) icon).getBitmap());
            e.printStackTrace();
        }

        final View thumbImageView = view.findViewById(R.id.ingredientsImage);
        thumbImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumbImageView, image);
            }
        });

        mShortAnimationDurationEffect = getResources().getInteger(android.R.integer.config_shortAnimTime);


        if (prod.getIngredients() == null) {
            fisrtLinear.setVisibility(LinearLayout.GONE);
        } else {
            ingredientsList.setText(prod.getIngredients());
        }
        if (prod.getAllergens() == null) {
            secondLinear.setVisibility(LinearLayout.GONE);
        } else {
            sostanzeList.setText(prod.getAllergens());
        }
        if (prod.getAdditives() == null) {
            thirdLinear.setVisibility(LinearLayout.GONE);
        } else {
            additiviList.setText(prod.getAdditives());
        }
        if (prod.getIngredientPalmOil() == null) {
            fourLinear.setVisibility(LinearLayout.GONE);
        } else {
            olioList.setText(prod.getIngredientPalmOil());
        }
        if (prod.getTraces() == null) {
            fiveLinear.setVisibility(LinearLayout.GONE);
        } else {
            tracesList.setText(prod.getTraces());
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AllergensController.checkProduct(getContext(), prod.getAllergens());
    }

    public Bitmap downloadImage(String imageURL) throws Exception {
        URL newurl = new URL(imageURL);
        Bitmap logo = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        return logo;
    }

    private void zoomImageFromThumb(final View thumbView, Bitmap imageResId) {
        if (mCurrentAnimatorEffect != null) {
            mCurrentAnimatorEffect.cancel();
        }

        final ImageView expandedImageView = (ImageView) view.findViewById(
                R.id.expanded_image);
        expandedImageView.setImageBitmap(imageResId);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        view.findViewById(R.id.frame_layout).getGlobalVisibleRect(finalBounds, globalOffset);
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
        set.play(ObjectAnimator.ofFloat(expandedImageView, View.X,
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
                                        View.Y, startBounds.top))
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
}
