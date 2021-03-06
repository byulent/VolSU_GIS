/*
 * Project:  NextGIS Mobile
 * Purpose:  Mobile GIS for Android.
 * Author:   Dmitry Baryshnikov (aka Bishop), bishop.dev@gmail.com
 * Author:   NikitaFeodonit, nfeodonit@yandex.com
 * Author:   Stanislav Petriakov, becomeglory@gmail.com
 * *****************************************************************************
 * Copyright (c) 2012-2015. NextGIS, info@nextgis.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.volsu.maplib.api;

import android.location.Location;

/**
 * Interface for listen location events
 * @author Dmitry Baryshnikov <dmitry.baryshnikov@nextgis.com>
 */
public interface GpsEventListener
{
    /**
     * Executed then location object changed
     * @param location object
     */
    void onLocationChanged(Location location);

    /**
     * Executed then location object changed in gather statistic mode, for best accuracy
     * @param location object
     */
    void onBestLocationChanged(Location location);

    /**
     * Executed then GPS status changed
     * @param event Id
     */
    void onGpsStatusChanged(int event);
}
