package rs.cod3rs.shopifine.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;
import java.util.stream.Collectors;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.ProductCategory;
import rs.cod3rs.shopifine.hateoas.product_categories.ProductCategoryResponseData;
import rs.cod3rs.shopifine.http.ProductCategories;

@EFragment(R.layout.dialog_filters)
public class FiltersFragmentDialog extends DialogFragment {

    public static String SELECTED_PRICE_RANGE_ARG = "selected_price_range";
    public static String SELECTED_CATEGORY_ARG = "selected_category";
    private static int SELECTED_NONE = -1;

    @RestService
    ProductCategories productCategories;

    @ViewById
    RadioGroup priceFilters;

    @ViewById
    RadioGroup categoryFilters;

    private FiltersDialogListener listener;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            listener = (FiltersDialogListener) context;
        } catch (final ClassCastException e) {
            Log.e(getClass().getSimpleName(), String.format("%s must implement FiltersDialogListener", context.toString()));
        }
    }

    @AfterViews
    void setupProductCategories() {
        getProductCategories();
    }

    @Background
    void getProductCategories() {
        final List<ProductCategory> categories = productCategories.retrieveAll().getData()
                .stream().map(ProductCategoryResponseData::toDomain).collect(Collectors.toList());

        populateProductCategories(categories);
    }

    @UiThread
    void populateProductCategories(final List<ProductCategory> categories) {
        categories.forEach(category -> {
            final RadioButton rb = categoryRadioButton(category);

            final Bundle bundle = this.getArguments();
            if (bundle != null) {
                final int categoryId = bundle.getInt(SELECTED_CATEGORY_ARG, SELECTED_NONE);
                if (categoryId != SELECTED_NONE && category.id.intValue() == categoryId) {
                    rb.setChecked(true);
                }
            }

            categoryFilters.addView(rb);
        });

        updateSelectedFields();
    }

    @Click
    void applyFilters() {
        listener.onFinishFilterDialog(priceFilters.getCheckedRadioButtonId(), categoryFilters.getCheckedRadioButtonId());
        finishDialog();
    }

    @Click
    void resetFilters() {
        priceFilters.clearCheck();
        categoryFilters.clearCheck();
    }

    @UiThread
    void updateSelectedFields() {
        final Bundle bundle = this.getArguments();
        if (bundle != null) {
            final int priceRangeId = bundle.getInt(SELECTED_PRICE_RANGE_ARG, SELECTED_NONE);

            if (priceRangeId != SELECTED_NONE) {
                priceFilters.check(priceRangeId);
            }
        }
    }

    private RadioButton categoryRadioButton(final ProductCategory category) {
        final RadioButton rb = new RadioButton(getContext());

        rb.setId(category.id.intValue());
        rb.setText(category.name);
        rb.setTextColor(getResources().getColor(R.color.colorWhite));

        return rb;
    }

    private void finishDialog() {
        final Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(R.layout.dialog_filters));
        if (prev != null) {
            final DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }

    public interface FiltersDialogListener {
        void onFinishFilterDialog(Integer priceFilterId, Integer categoryFilterId);
    }

}
