/*
 * Project:  NextGIS Mobile
 * Purpose:  Mobile GIS for Android.
 * Author:   Stanislav Petriakov, becomeglory@gmail.com
 * *****************************************************************************
 * Copyright (c) 2016-2017 NextGIS, info@nextgis.com
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

package com.volsu.maplibui.fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.volsu.maplib.api.ITextStyle;
import com.volsu.maplib.datasource.Field;
import com.volsu.maplib.display.SimpleLineStyle;
import com.volsu.maplib.display.SimpleMarkerStyle;
import com.volsu.maplib.display.SimplePolygonStyle;
import com.volsu.maplib.display.Style;
import com.volsu.maplib.map.VectorLayer;
import com.volsu.maplib.util.Constants;
import com.volsu.maplib.util.GeoConstants;
import com.volsu.maplibui.R;
import com.volsu.maplibui.dialog.StyledDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;

public class StyleFragment extends StyledDialogFragment implements View.OnClickListener {
    protected ImageView mColorFillImage, mColorStrokeImage;
    protected TextView mColorFillName, mColorStrokeName;
    protected EditText mEditText;
    protected Spinner mField;
    protected CheckBox mTextEnabled;
    protected SwitchCompat mNotHardcoded;
    protected int mFillColor, mStrokeColor;
    protected Style mStyle;
    protected VectorLayer mLayer;

    public StyleFragment() {

    }

    public void setStyle(Style style) {
        mStyle = style;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mStyle == null)
            return null;

        View body = null;
        mFillColor = mStyle.getColor();
        if (mStyle instanceof SimpleMarkerStyle) {
            body = inflater.inflate(R.layout.style_marker, container, false);
            inflateMarker(body);
        } else if (mStyle instanceof SimpleLineStyle) {
            body = inflater.inflate(R.layout.style_line, container, false);
            inflateLine(body);
        } else if (mStyle instanceof SimplePolygonStyle) {
            body = inflater.inflate(R.layout.style_polygon, container, false);
            inflatePolygon(body);
        }

        inflateText(body);

        setView(body, true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void inflateMarker(View v) {
        Spinner type = (Spinner) v.findViewById(R.id.type);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((SimpleMarkerStyle) mStyle).setType(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        type.setSelection(((SimpleMarkerStyle) mStyle).getType() - 1);

        float size = ((SimpleMarkerStyle) mStyle).getSize();
        EditText sizeText = (EditText) v.findViewById(R.id.size);
        sizeText.setText(String.format(Locale.getDefault(), "%.0f", size));
        sizeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    ((SimpleMarkerStyle) mStyle).setSize(Float.parseFloat(s.toString()));
                } catch (Exception ignored) { }
            }
        });

        mStrokeColor = mStyle.getOutColor();
        mColorFillName = (TextView) v.findViewById(R.id.color_fill_name);
        mColorFillImage = (ImageView) v.findViewById(R.id.color_fill_ring);
        mColorStrokeName = (TextView) v.findViewById(R.id.color_stroke_name);
        mColorStrokeImage = (ImageView) v.findViewById(R.id.color_stroke_ring);

        LinearLayout color_fill = (LinearLayout) v.findViewById(R.id.color_fill);
        LinearLayout color_stroke = (LinearLayout) v.findViewById(R.id.color_stroke);
        color_fill.setOnClickListener(this);
        color_stroke.setOnClickListener(this);
        setFillColor(mFillColor);
        setStrokeColor(mStrokeColor);

        float width = mStyle.getWidth();
        EditText widthText = (EditText) v.findViewById(R.id.width);
        widthText.setText(String.format(Locale.getDefault(), "%.0f", width));
        widthText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mStyle.setWidth(Float.parseFloat(s.toString()));
                } catch (Exception ignored) { }
            }
        });
    }

    private void inflateLine(View v) {
        mStrokeColor = mStyle.getOutColor();

        mColorFillName = (TextView) v.findViewById(R.id.color_fill_name);
        mColorFillImage = (ImageView) v.findViewById(R.id.color_fill_ring);
        mColorStrokeName = (TextView) v.findViewById(R.id.color_stroke_name);
        mColorStrokeImage = (ImageView) v.findViewById(R.id.color_stroke_ring);

        LinearLayout color_fill = (LinearLayout) v.findViewById(R.id.color_fill);
        LinearLayout color_stroke = (LinearLayout) v.findViewById(R.id.color_stroke);
        color_fill.setOnClickListener(this);
        color_stroke.setOnClickListener(this);
        setFillColor(mFillColor);
        setStrokeColor(mStrokeColor);

        float width = mStyle.getWidth();
        EditText widthText = (EditText) v.findViewById(R.id.width);
        widthText.setText(String.format(Locale.getDefault(), "%.0f", width));
        widthText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mStyle.setWidth(Float.parseFloat(s.toString()));
                } catch (Exception ignored) { }
            }
        });

        Spinner type = (Spinner) v.findViewById(R.id.type);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((SimpleLineStyle) mStyle).setType(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        type.setSelection(((SimpleLineStyle) mStyle).getType() - 1);
    }

    private void inflatePolygon(View v) {
        float width = mStyle.getWidth();
        boolean fill = ((SimplePolygonStyle) mStyle).isFill();
        mStrokeColor = mStyle.getOutColor();

        mColorFillName = (TextView) v.findViewById(R.id.color_fill_name);
        mColorFillImage = (ImageView) v.findViewById(R.id.color_fill_ring);
        mColorStrokeName = (TextView) v.findViewById(R.id.color_stroke_name);
        mColorStrokeImage = (ImageView) v.findViewById(R.id.color_stroke_ring);

        CheckBox fillCheck = (CheckBox) v.findViewById(R.id.fill);
        fillCheck.setChecked(fill);
        fillCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((SimplePolygonStyle) mStyle).setFill(isChecked);
            }
        });

        EditText widthText = (EditText) v.findViewById(R.id.width);
        widthText.setText(String.format(Locale.getDefault(), "%.0f", width));
        widthText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mStyle.setWidth(Float.parseFloat(s.toString()));
                } catch (Exception ignored) { }
            }
        });

        LinearLayout color_fill = (LinearLayout) v.findViewById(R.id.color_fill);
        color_fill.setOnClickListener(this);
        setFillColor(mFillColor);
        LinearLayout color_stroke = (LinearLayout) v.findViewById(R.id.color_stroke);
        color_stroke.setOnClickListener(this);
        setStrokeColor(mStrokeColor);
    }

    private void inflateText(View body) {
        if (!(mStyle instanceof ITextStyle))
            return;

        final ITextStyle style = (ITextStyle) mStyle;
        mTextEnabled = (CheckBox) body.findViewById(R.id.text_enabled);
        if (mStyle instanceof SimpleMarkerStyle) {
            mTextEnabled.setEnabled(false);
        }

        mNotHardcoded = (SwitchCompat) body.findViewById(R.id.not_hardcoded);
        mEditText = (EditText) body.findViewById(R.id.text);
        mEditText.setText(style.getText());
        mField = (Spinner) body.findViewById(R.id.field);

        String field = style.getField();
        boolean hasText = style.getText() != null;
        boolean hasField = field != null;

        mTextEnabled.setChecked(hasField || hasText);
        mNotHardcoded.setEnabled(mTextEnabled.isChecked());
        mEditText.setEnabled(mTextEnabled.isChecked());
        mField.setEnabled(mTextEnabled.isChecked());

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                style.setText(s.toString());
            }
        });

        final List<Field> mFields = mLayer.getFields();
        mFields.add(0, new Field(GeoConstants.FTInteger, Constants.FIELD_ID, Constants.FIELD_ID));
        final List<String> fieldNames = new ArrayList<>();
        int id = -1;
        for (int i = 0; i < mFields.size(); i++) {
            fieldNames.add(mFields.get(i).getAlias());
            if (mFields.get(i).getName().equals(field))
                id = i;
        }

        ArrayAdapter fieldAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, fieldNames);
        mField.setAdapter(fieldAdapter);
        if (hasField && id > -1)
            mField.setSelection(id);

        mField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                style.setField(mFields.get(position).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTextEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNotHardcoded.setEnabled(isChecked);
                mEditText.setEnabled(isChecked);
                mField.setEnabled(isChecked);

                if (!isChecked) {
                    style.setField(null);
                    style.setText(null);
                } else {
                    if (mNotHardcoded.isChecked()) {
                        style.setField(mFields.get(mField.getSelectedItemPosition()).getName());
                        style.setText(null);
                    } else {
                        style.setField(null);
                        style.setText(mEditText.getText().toString());
                    }
                }
            }
        });

        mNotHardcoded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mEditText.setVisibility(isChecked ? View.GONE : View.VISIBLE);
                mField.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                style.setField(isChecked ? mFields.get(mField.getSelectedItemPosition()).getName(): null);
            }
        });
        mNotHardcoded.setChecked(hasField);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setFillColor(int color) {
        setColor(mColorFillImage, mColorFillName, color);
    }

    protected void setStrokeColor(int color) {
        setColor(mColorStrokeImage, mColorStrokeName, color);
    }

    private static void setColor(ImageView image, TextView text, int color) {
        // set color
        GradientDrawable sd = (GradientDrawable) image.getDrawable();
        sd.setColor(color);
        image.invalidate();

        // set color name
        text.setText(getColorName(color));
    }

    protected static String getColorName(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.color_fill) {//show colors dialog
            AmbilWarnaDialog dialog = new AmbilWarnaDialog(v.getContext(), mFillColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    mFillColor = color;
                    setFillColor(color);
                    mStyle.setColor(color);
                }

                @Override
                public void onCancel(AmbilWarnaDialog dialog) {

                }
            });

            dialog.show();
        } else if (i == R.id.color_stroke) {//show colors dialog
            AmbilWarnaDialog dialog = new AmbilWarnaDialog(v.getContext(), mStrokeColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    mStrokeColor = color;
                    setStrokeColor(color);

                    if (mStyle instanceof SimpleMarkerStyle)
                        mStyle.setOutColor(color);
                    else if (mStyle instanceof SimpleLineStyle)
                        mStyle.setOutColor(color);
                    else if (mStyle instanceof SimplePolygonStyle)
                        mStyle.setOutColor(color);
                }

                @Override
                public void onCancel(AmbilWarnaDialog dialog) {

                }
            });

            dialog.show();
        }
    }

    public void setLayer(VectorLayer layer) {
        mLayer = layer;
    }
}