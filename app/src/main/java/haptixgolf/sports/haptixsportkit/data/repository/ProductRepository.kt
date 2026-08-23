package haptixgolf.sports.haptixsportkit.data.repository

import haptixgolf.sports.haptixsportkit.data.model.Product
import haptixgolf.sports.haptixsportkit.data.model.ProductCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRepository {
    private val products = listOf(
        Product(
            1,
            "Tour Control Golf Balls",
            "A dozen three-piece urethane balls engineered for a penetrating flight, dependable greenside spin and a soft, responsive feel.",
            ProductCategory.GOLF,
            34.99,
            "https://images.unsplash.com/photo-1587174486073-ae5e5cff23aa?w=1200"
        ),
        Product(
            2,
            "Carbon Pro Driver",
            "A forgiving 10.5-degree driver with a lightweight carbon crown and adjustable hosel for confident launch from every tee.",
            ProductCategory.GOLF,
            229.00,
            "https://images.unsplash.com/photo-1535131749006-b7f58c99034b?w=1200"
        ),
        Product(
            3,
            "Precision Blade Putter",
            "Balanced stainless-steel blade putter with a milled face, clear alignment line and tacky all-weather grip.",
            ProductCategory.GOLF,
            119.50,
            "https://images.unsplash.com/photo-1593111774240-d529f12cf4bb?w=1200"
        ),
        Product(
            4,
            "Match Point Tennis Racket",
            "A fast 300 g graphite frame combining easy power, stable volleys and spin-friendly 16-by-19 string pattern.",
            ProductCategory.RACKET_SPORTS,
            139.99,
            "https://images.unsplash.com/photo-1617083934555-ac7d4b5b279d?w=1200"
        ),
        Product(
            5,
            "Carbon Strike Padel Racket",
            "Teardrop carbon face with medium-density foam for a versatile balance of controlled defence and explosive finishing shots.",
            ProductCategory.RACKET_SPORTS,
            124.00,
            "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?w=1200"
        ),
        Product(
            6,
            "Adjustable Kettlebell 18 kg",
            "Space-saving cast-iron kettlebell with quick weight selection for swings, squats, presses and full-body circuits.",
            ProductCategory.FITNESS,
            89.99,
            "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=1200"
        ),
        Product(
            7,
            "Resistance Band Training Set",
            "Five durable resistance levels, handles, ankle straps and door anchor packed into a travel-ready training bag.",
            ProductCategory.TRAINING,
            28.50,
            "https://images.unsplash.com/photo-1598289431512-b97b0917affc?w=1200"
        ),
        Product(
            8,
            "Agility Speed Kit",
            "A complete footwork station with a six-metre ladder, twelve cones and four hurdles for sharper acceleration and control.",
            ProductCategory.TRAINING,
            44.99,
            "https://images.unsplash.com/photo-1517963879433-6ad2b056d712?w=1200"
        ),
        Product(
            9,
            "Performance Training Tee",
            "Breathable four-way stretch fabric with flat seams and a close athletic cut that stays comfortable through hard sessions.",
            ProductCategory.APPAREL,
            32.00,
            "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=1200"
        ),
        Product(
            10,
            "All-Sport Duffel 40L",
            "Water-resistant kit bag with ventilated shoe storage, padded shoulder strap and quick-access valuables pocket.",
            ProductCategory.APPAREL,
            54.95,
            "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=1200"
        ),
        Product(
            11,
            "Core Balance Trainer",
            "Non-slip half-dome trainer for balance drills, core strength and controlled rehabilitation movements.",
            ProductCategory.FITNESS,
            74.00,
            "https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=1200"
        ),
        Product(
            12,
            "Indoor Putting Mat",
            "Three-metre true-roll practice surface with distance markers and automatic ball return for repeatable home practice.",
            ProductCategory.GOLF,
            68.99,
            "https://images.unsplash.com/photo-1591491653056-8c3dbbb74d14?w=1200"
        )
    )

    fun observeById(id: Int): Flow<Product?> = flowOf(products.find { it.id == id })

    fun getById(id: Int): Product? = products.find { it.id == id }

    fun observeAll(): Flow<List<Product>> = flowOf(products)
}
