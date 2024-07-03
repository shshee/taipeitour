package com.tangerine.core.api.attractions

import com.tangerine.core.api.base.BaseRepo
import com.tangerine.core.model.AttractionsResp
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.spyk
import junit.framework.Assert.assertTrue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class AttractionsRepoTest {

    @get:Rule
    val mockkRule =
        MockKRule(this) //Use this then we don't have to call MockAnnotations.init(this) manually

    @MockK
    private lateinit var service: AttractionsService

    private lateinit var repo: AttractionsRepo

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        every { service.getAttractions(any(), any()) } returns generateResponse(true)
        every { service.getAttractions("", any()) } returns generateResponse(false)

        repo = AttractionsRepo(UnconfinedTestDispatcher(),service)
    }

    @Test
    fun `receive responses when get attractions`() = runTest {
        val caseSuccess = repo.getAttractions("en", 1).first()
        assertTrue("Response: $caseSuccess", caseSuccess is BaseRepo.ApiResponse.Success<*> && caseSuccess.response != null)
    }

    @Test
    fun `throw errors when get attractions`() = runTest {
        val caseFailure = repo.getAttractions("", null).first()
        assertEquals(true, caseFailure is BaseRepo.ApiResponse.Failure)
    }

    private fun generateResponse(isSuccess: Boolean) = spyk<Call<AttractionsResp>> {
        every { clone().execute() } returns Response.success(
            if (isSuccess) AttractionsResp(
                total = 0,
                data = emptyList()
            ) else null
        )

        //with spyK execute() will run as our code otherwise mockK will run nothing
    }
}