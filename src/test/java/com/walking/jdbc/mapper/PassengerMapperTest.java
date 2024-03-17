package com.walking.jdbc.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassengerMapperTest {

    private final PassengerMapper passengerMapper = new PassengerMapper();

    @Test
    void map_value_success() throws SQLException {
//        given
        var rs = mock(ResultSet.class);
        doReturn(true).when(rs).next();
        doReturn(new Date(0)).when(rs).getDate(any());

//        when
        var actual = passengerMapper.map(rs);

//        then
        assertTrue(actual.isPresent());
        verify(rs).next();

        verify(rs, times(2)).getString(any());
        verify(rs).getLong(any());
        verify(rs).getBoolean(any());
        verify(rs).getDate(any());
        verify(rs).getTimestamp(any());
    }

    @Test
    void map_empty_success() throws SQLException {
//        given
        var rs = mock(ResultSet.class);

//        when
        var actual = passengerMapper.map(rs);

//        then
        assertEquals(Optional.empty(), actual);
        verify(rs).next();

        verify(rs, never()).getString(any());
        verify(rs, never()).getLong(any());
        verify(rs, never()).getBoolean(any());
        verify(rs, never()).getDate(any());
        verify(rs, never()).getTimestamp(any());
    }

    @Test
    void map_SQLException_failed() throws SQLException {
//        given
        var rs = mock(ResultSet.class);
        doThrow(SQLException.class).when(rs).next();

//        when
        Executable executable = () -> passengerMapper.map(rs);

//        then
        assertThrows(SQLException.class, executable);
        verify(rs).next();

        verify(rs, never()).getString(any());
        verify(rs, never()).getLong(any());
        verify(rs, never()).getBoolean(any());
        verify(rs, never()).getDate(any());
        verify(rs, never()).getTimestamp(any());
    }

    @Test
    void mapMany_empty_success() throws SQLException {
//        given
        var rs = mock(ResultSet.class);

//        when
        var actual = passengerMapper.mapMany(rs);

//        then
        assertTrue(actual.isEmpty());
        verify(rs).next();

        verify(rs, never()).getString(any());
        verify(rs, never()).getLong(any());
        verify(rs, never()).getBoolean(any());
        verify(rs, never()).getDate(any());
        verify(rs, never()).getTimestamp(any());
    }

    @Test
    void mapMany_value_success() throws SQLException {
//        given
        var rs = mock(ResultSet.class);
        doReturn(true)
                .doReturn(false)
                .when(rs).next();
        doReturn(new Date(0)).when(rs).getDate(any());

//        when
        var actual = passengerMapper.mapMany(rs);

//        then
        assertEquals(1, actual.size());
        verify(rs, times(2)).next();

        verify(rs, times(2)).getString(any());
        verify(rs).getLong(any());
        verify(rs).getBoolean(any());
        verify(rs).getDate(any());
        verify(rs).getTimestamp(any());
    }

    @Test
    void mapMany_multipleValues_success() throws SQLException {
//        given
        var rs = mock(ResultSet.class);
        doReturn(true)
                .doReturn(true)
                .doReturn(false)
                .when(rs).next();
        doReturn(new Date(0)).when(rs).getDate(any());

//        when
        var actual = passengerMapper.mapMany(rs);

//        then
        assertEquals(2, actual.size());
        verify(rs, times(3)).next();

        verify(rs, times(4)).getString(any());
        verify(rs, times(2)).getLong(any());
        verify(rs, times(2)).getBoolean(any());
        verify(rs, times(2)).getDate(any());
        verify(rs, times(2)).getTimestamp(any());
    }

    @Test
    void mapMany_SQLException_failed() throws SQLException {
//        given
        var rs = mock(ResultSet.class);
        doThrow(SQLException.class).when(rs).next();

//        when
        Executable executable = () -> passengerMapper.mapMany(rs);

//        then
        assertThrows(SQLException.class, executable);
        verify(rs).next();

        verify(rs, never()).getString(any());
        verify(rs, never()).getLong(any());
        verify(rs, never()).getBoolean(any());
        verify(rs, never()).getDate(any());
        verify(rs, never()).getTimestamp(any());
    }
}