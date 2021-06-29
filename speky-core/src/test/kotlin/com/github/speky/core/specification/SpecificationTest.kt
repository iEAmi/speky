package com.github.speky.core.specification

import com.github.speky.core.ClassRef
import com.github.speky.core.Lens
import com.github.speky.core.Lens.Companion.on
import com.github.speky.core.specification.Filter.Companion.like
import com.github.speky.core.specification.Order.Companion.desc
import com.github.speky.core.specification.Specification.Companion.from
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

private data class Person(val id: Long, val name: String, val age: Long)
private data class Product(val id: Long, val name: String)
private data class PersonProduct(val person: Person, val product: Product)

internal class SpecificationTest : FunSpec({

  test("invoke(Alias) should return Source.Single") {
    val spec = from<Int>("alias")

    spec.shouldBeInstanceOf<Source.Single<Int>>()
    spec.alias shouldBe Alias.of("alias")
    spec.alias.classRef shouldBe ClassRef.of()
    spec.classRef shouldBe spec.classRef
  }

  test("invoke(String) should return Source.Single") {
    val spec = from<Int>("alias")

    spec.shouldBeInstanceOf<Source.Single<Int>>()
    spec.alias shouldBe Alias.of("alias")
    spec.alias.classRef shouldBe ClassRef.of()
    spec.classRef shouldBe spec.classRef
  }

  test("simple specification") {
    val allSpec = from<Person>("p")
      .select()
      .filter(like(on<Person, String>("name"), "iEAmi"))
      .order(desc(on<Person, Long>("age")))
      .size(10, 0)

    allSpec.shouldBeInstanceOf<Sized<Person, Long>>()
    allSpec.limit shouldBe 10
    allSpec.offset shouldBe 0

    allSpec.alias.shouldBeInstanceOf<Alias.Single<Person>>()
    (allSpec.alias as Alias.Single<*>).value shouldBe "p"
    allSpec.alias.classRef shouldBe allSpec.classRef

    allSpec.classRef shouldBe ClassRef("Person", "com.github.speky.core.specification.Person")

    allSpec.delegate.shouldBeInstanceOf<Ordered<Person, Long>>()
    (allSpec.delegate as Ordered<*, *>).order.lens shouldBe on<Person, Long>("age")
    (allSpec.delegate as Ordered<*, *>).order.shouldBeInstanceOf<Order.Descending<Person, Long>>()
    (allSpec.delegate as Ordered<*, *>).delegate.shouldBeInstanceOf<Filtered<Person, String>>()

    ((allSpec.delegate as Ordered<*, *>).delegate as Filtered<*, *>).filter
      .lens shouldBe on<Person, String>("name")
    ((allSpec.delegate as Ordered<*, *>).delegate as Filtered<*, *>)
      .filter.shouldBeInstanceOf<Filter.Like<Person>>()
    (((allSpec.delegate as Ordered<*, *>).delegate as Filtered<*, *>).filter as Filter.Like<*>)
      .value shouldBe "iEAmi"

    ((allSpec.delegate as Ordered<*, *>).delegate as Filtered<*, *>)
      .delegate.shouldBeInstanceOf<Selected.All<Person>>()

    (((allSpec.delegate as Ordered<*, *>).delegate as Filtered<*, *>).delegate as Selected.All<*>)
      .source.shouldBeInstanceOf<Source.Single<Person>>()
  }

  test("select(Lens) specification") {
    val spec = from<Person>("p")
      .select(on<Person, Long>("id"))
      .filter(like(on<Person, String>("name"), "iEAmi"))
      .order(desc(on<Person, Long>("age")))
      .size(10, 0)

    spec.shouldBeInstanceOf<Sized<Person, Long>>()
    spec.limit shouldBe 10
    spec.offset shouldBe 0

    spec.alias.shouldBeInstanceOf<Alias.Single<Person>>()
    (spec.alias as Alias.Single<*>).value shouldBe "p"
    spec.alias.classRef shouldBe spec.classRef

    spec.classRef shouldBe ClassRef("Person", "com.github.speky.core.specification.Person")

    spec.delegate.shouldBeInstanceOf<Ordered<Person, Long>>()
    (spec.delegate as Ordered<*, *>).order.lens shouldBe on<Person, Long>("age")
    (spec.delegate as Ordered<*, *>).order.shouldBeInstanceOf<Order.Descending<Person, Long>>()
    (spec.delegate as Ordered<*, *>).delegate.shouldBeInstanceOf<Filtered<Person, String>>()

    ((spec.delegate as Ordered<*, *>).delegate as Filtered<*, *>).filter
      .lens shouldBe on<Person, String>("name")
    ((spec.delegate as Ordered<*, *>).delegate as Filtered<*, *>)
      .filter.shouldBeInstanceOf<Filter.Like<Person>>()
    (((spec.delegate as Ordered<*, *>).delegate as Filtered<*, *>)
      .filter as Filter.Like<*>).value shouldBe "iEAmi"

    ((spec.delegate as Ordered<*, *>).delegate as Filtered<*, *>)
      .delegate.shouldBeInstanceOf<Selected.Some<Person>>()

    (((spec.delegate as Ordered<*, *>).delegate as Filtered<*, *>).delegate as Selected.Some<*>)
      .source.shouldBeInstanceOf<Source.Single<Person>>()
  }

  test("crossJoin specification") {
    val filter = like(on<PersonProduct, Person>("person").zoom(on("name")), "iEAmi")
    val order = desc(on<PersonProduct, Person>("person").zoom(on<Person, Long>("age")))

    with(Filter.show) {
      filter.show() shouldBe "PersonProduct.person.name like '%iEAmi%'"
    }

    with(Order.show) {
      order.show() shouldBe "order by PersonProduct.person.age desc"
    }

    val spec = from<Person>("p").crossJoin<Product, PersonProduct>("pr")
      .select()
      .filter(filter)
      .order(order)
      .size(10, 0)

    spec.shouldBeInstanceOf<Sized<PersonProduct, Long>>()
    spec.limit shouldBe 10
    spec.offset shouldBe 0

    spec.alias.shouldBeInstanceOf<Alias.Multiply<PersonProduct, Person, Product>>()
    (spec.alias as Alias.Multiply<*, *, *>).left.shouldBeInstanceOf<Alias.Single<Person>>()
    ((spec.alias as Alias.Multiply<*, *, *>).left as Alias.Single<*>).value shouldBe "p"

    (spec.alias as Alias.Multiply<*, *, *>).right.shouldBeInstanceOf<Alias.Single<Product>>()
    ((spec.alias as Alias.Multiply<*, *, *>).right as Alias.Single<*>).value shouldBe "pr"

    spec.alias.classRef shouldBe spec.classRef

    spec.classRef shouldBe ClassRef(
      "PersonProduct",
      "com.github.speky.core.specification.PersonProduct"
    )

    spec.delegate.shouldBeInstanceOf<Ordered<PersonProduct, Long>>()
    (spec.delegate as Ordered<*, *>).order
      .lens.shouldBeInstanceOf<Lens.Zoom<Long, Person, PersonProduct>>()
    (spec.delegate as Ordered<*, *>)
      .order.shouldBeInstanceOf<Order.Descending<PersonProduct, Long>>()
    (spec.delegate as Ordered<*, *>)
      .delegate.shouldBeInstanceOf<Filtered<PersonProduct, String>>()

    ((spec.delegate as Ordered<*, *>).delegate as Filtered<*, *>).filter
      .lens.shouldBeInstanceOf<Lens.Zoom<String, Person, PersonProduct>>()
    ((spec.delegate as Ordered<*, *>).delegate as Filtered<*, *>)
      .filter.shouldBeInstanceOf<Filter.Like<PersonProduct>>()
    (((spec.delegate as Ordered<*, *>).delegate as Filtered<*, *>).filter as Filter.Like<*>)
      .value shouldBe "iEAmi"

    ((spec.delegate as Ordered<*, *>).delegate as Filtered<*, *>)
      .delegate.shouldBeInstanceOf<Selected.All<PersonProduct>>()

    (((spec.delegate as Ordered<*, *>).delegate as Filtered<*, *>).delegate as Selected.All<*>)
      .source.shouldBeInstanceOf<Source.Mix.CrossJoin<Person, Product, PersonProduct>>()
  }

  test("insertOn table") {
    val spec = Specification.insertOn<Person>(
      Value.of("name", "Ahmad"),
      Value.of("age", 10L)
    )

    val insert = Sink.insert<Person>(
      listOf(
        Value.of("name", "Ahmad"),
        Value.of("age", 10L)
      )
    )

    spec.alias shouldBe insert.alias
    spec.values shouldContainAll insert.values
  }

  test("updateOn table update all") {
    val spec = Specification.updateOn<Person>(
      Value.of("name", "Ahmad"),
      Value.of("age", 10L)
    )

    val update = Sink.update<Person>(
      setOf(
        Value.of("name", "Ahmad"),
        Value.of("age", 10L)
      )
    )

    spec.alias shouldBe update.alias
    spec.values shouldContainAll update.values
  }
})
