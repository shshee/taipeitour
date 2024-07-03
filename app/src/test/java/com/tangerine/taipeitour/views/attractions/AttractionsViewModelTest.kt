package com.tangerine.taipeitour.views.attractions

import com.tangerine.core.api.attractions.AttractionsRepo
import com.tangerine.core.api.base.BaseRepo
import com.tangerine.core.model.AttractionsResp
import com.tangerine.core.model.UiState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AttractionsViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var repo: AttractionsRepo

    private lateinit var viewModel: AttractionsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        coEvery { repo.getAttractions(any(), any()) }.coAnswers {
            flow {
                emit(BaseRepo.ApiResponse.Success(AttractionsResp(total = 5, data = emptyList())))
            }.flowOn(UnconfinedTestDispatcher())
        }

        viewModel = AttractionsViewModel(UnconfinedTestDispatcher(),repo)
    }

    @Test
    fun `get attractions`() {
        viewModel.getAttractions("en", false)
        assertEquals(true, viewModel.attractionUiState.value.state == UiState.SUCCESS)
    }
}