package di
import com.example.simplenoteapp.domain.useCase.NoteUseCases
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}

open class BaseKoinComponent: KoinComponent

class KoinHelper : BaseKoinComponent() {
    private val _noteUseCase: NoteUseCases by inject()
    val noteUseCases get() = _noteUseCase
}
