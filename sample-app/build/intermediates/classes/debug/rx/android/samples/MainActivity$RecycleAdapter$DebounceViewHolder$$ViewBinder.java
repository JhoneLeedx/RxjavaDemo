// Generated code from Butter Knife. Do not modify!
package rx.android.samples;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$RecycleAdapter$DebounceViewHolder$$ViewBinder<T extends rx.android.samples.MainActivity.RecycleAdapter.DebounceViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492978, "field 'imageIv'");
    target.imageIv = finder.castView(view, 2131492978, "field 'imageIv'");
    view = finder.findRequiredView(source, 2131492979, "field 'descriptionTv'");
    target.descriptionTv = finder.castView(view, 2131492979, "field 'descriptionTv'");
  }

  @Override public void unbind(T target) {
    target.imageIv = null;
    target.descriptionTv = null;
  }
}
