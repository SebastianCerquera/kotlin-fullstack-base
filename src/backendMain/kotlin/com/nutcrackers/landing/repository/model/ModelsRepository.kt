import com.nutcrackers.landing.Browser
import com.nutcrackers.landing.Locale
import com.nutcrackers.landing.Session
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor



@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "browser")
data class BrowserRepositoryModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Embedded
    var browser: Browser.Companion? = null
)


@AllArgsConstructor
@NoArgsConstructor
@Entity(name="session")
data class SessionRepositoryModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Embedded
    var session: Session.Companion? = null

)

@AllArgsConstructor
@NoArgsConstructor
@Entity(name="locale")
data class LocaleRepositoryModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Embedded
    var locale: Locale.Companion? = null
)