package com.tangerine.core.api.attractions

import com.tangerine.core.api.base.BaseRepo
import com.tangerine.core.model.AttractionsResp
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.spyk
import junit.framework.Assert.assertTrue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
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

    @Before
    fun setUp() {
        every { service.getAttractions(any(), any()) } returns generateResponse(true)
        every { service.getAttractions("", any()) } returns generateResponse(false)

        repo = AttractionsRepo(service)
    }

    @Test
    fun `responses will receive when we call get attractions`() = runTest {
        val caseSuccess = repo.getAttractions("en", 1).first()
        val caseFailure = repo.getAttractions("", null).first()

        assertTrue("Response: $caseSuccess", caseSuccess is BaseRepo.ApiResponse.Success<*>)
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