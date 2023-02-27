import com.nutcrackers.landing.Browser
import com.nutcrackers.landing.Locale
import com.nutcrackers.landing.Session
import com.nutcrackers.landing.repository.interfaces.IBrowserRepository
import com.nutcrackers.landing.repository.interfaces.ILocaleRepository
import com.nutcrackers.landing.repository.interfaces.ISessionRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/browser")
class BrowserController(val browserRepository: IBrowserRepository) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createBrowser(@RequestBody browser: Browser): BrowserRepositoryModel {
        val browserEntity= Browser.let {
            BrowserRepositoryModel(null, it)
        }
        return browserRepository.save(browserEntity)

    }
}

@RestController
@RequestMapping("/v1/session")
class SessionController(val sessionRepository: ISessionRepository) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createSession(@RequestBody session:Session): SessionRepositoryModel {
        val sessionEntity = Session.let {
            SessionRepositoryModel(null, it)
        }
        return sessionRepository.save(sessionEntity)
    }

}

@RestController
@RequestMapping("v1/locale")
class LocaleController(val localeRepository: ILocaleRepository) {

    fun createLocale(@RequestBody locale: Locale): LocaleRepositoryModel {
        val localeEntity = Locale.let {
            LocaleRepositoryModel(null, it)
        }

        return localeRepository.save(localeEntity)
    }
}
