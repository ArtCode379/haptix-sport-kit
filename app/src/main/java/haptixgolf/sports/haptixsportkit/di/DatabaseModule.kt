package haptixgolf.sports.haptixsportkit.di

import androidx.room.Room
import haptixgolf.sports.haptixsportkit.data.database.YJIJWDatabase
import org.koin.dsl.module

private const val DB_NAME = "yjijw_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = YJIJWDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<YJIJWDatabase>().cartItemDao() }

    single { get<YJIJWDatabase>().orderDao() }
}