import android.content.Context
import com.example.composecats.core.di.ApplicationScope
import com.example.composecats.core.di.DataLocalModule
import com.example.composecats.core.di.NetworkDataModule
import com.example.composecats.features.cats_detail.di.DetailComponent
import com.example.composecats.features.favourite.di.FavouriteModule
import com.example.composecats.features.feed_cats.di.FeedModule
import com.example.composecats.features.feed_cats.di.FeedViewModelModule
import com.example.composecats.core.di.ViewModelFactory
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataLocalModule::class,
        NetworkDataModule::class,
        FeedModule::class,
        FavouriteModule::class,
        FeedViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun getDetailScreenComponentFactory(): DetailComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent

    }

}