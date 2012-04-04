package com.monyetmabuk.rajawali.tutorials;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import rajawali.Camera;
import rajawali.Geometry3D;
import rajawali.materials.ParticleMaterial;
import rajawali.math.Number3D;
import rajawali.primitives.Particle;
import rajawali.util.ObjectColorPicker.ColorPickerInfo;
import android.opengl.GLES20;

public class ExampleParticleSystem extends Particle {
	protected Number3D mFriction;
	protected FloatBuffer mVelocityBuffer;
	protected float mTime;
	
	public ExampleParticleSystem() {
		super();
	}
	
	@Override
	protected void init() {
		mMaterial = new ParticleMaterial();
		mParticleShader = (ParticleMaterial)mMaterial;
		setDrawingMode(GLES20.GL_POINTS);
		setTransparent(true);
		
		final int numParticles = 5000;
		
		float[] vertices = new float[numParticles * 3];
		float[] velocity = new float[numParticles * 3];
		float[] textureCoords = new float[numParticles * 2];
		float[] normals = new float[numParticles * 3];
		float[] colors = new float[numParticles * 4];
		short[] indices = new short[numParticles];
		
		int index = 0;
		
		for(int i=0; i<numParticles; ++i) {
			index = i * 3;
			vertices[index] = 0;
			vertices[index + 1] = 0;
			vertices[index + 2] = 0;
			
			velocity[index] = -.5f + ((float)Math.random() * 1f);
			velocity[index + 1] = -.5f + ((float)Math.random() * 1f);
			velocity[index + 2] = ((float)Math.random() * 1f);
			
			normals[index] = 0;
			normals[index + 1] = 0;
			normals[index + 2] = 1;
			
			index = i * 2;
			textureCoords[i] = 0;
			textureCoords[i + 1] = 0;
			
			index = i * 4;
			colors[i] = 1;
			colors[i + 1] = i;
			colors[i + 2] = i;
			colors[i + 3] = i;
			
			indices[i] = (short)i;
		}
		
		mVelocityBuffer = ByteBuffer
				.allocateDirect(velocity.length * Geometry3D.FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mVelocityBuffer.put(velocity).position(0);
		
		mFriction = new Number3D(.95f, .95f, .95f);
		
		setData(vertices, normals, textureCoords, colors, indices);
	}
	
	public void setTime(float time) {
		mTime = time;
	}
	
	public float getTime() {
		return mTime;
	}
	
	@Override
	protected void setShaderParams(Camera camera) {
		super.setShaderParams(camera);
		mParticleShader.setFriction(mFriction);
		mParticleShader.setVelocity(mVelocityBuffer);
		mParticleShader.setMultiParticlesEnabled(true);
		mParticleShader.setTime(mTime);
	}
	
	@Override
	public void render(Camera camera, float[] projMatrix, float[] vMatrix,
			final float[] parentMatrix, ColorPickerInfo pickerInfo) {
		super.render(camera, projMatrix, vMatrix, parentMatrix, pickerInfo);
	}
}