package com.seligames.buildUp.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.seligames.buildUp.Constants;
import com.seligames.buildUp.entities.BlackPlayer;
import com.seligames.buildUp.entities.WhitePlayer;

public class MyContactListener implements ContactListener {

	private int wpFootCount;
	private int bpFootCount;

	private boolean wpVelocity = false;
	private boolean bpVelocity = false;

	private boolean loodNextLevel = false;
	private boolean playerDead = false;
	
	private Body wpBody;
	private Body bpBody;

	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		if (fixA == null || fixB == null)
			return;

		Filter fa = fixA.getFilterData();
		Filter fb = fixB.getFilterData();

		if (fa.categoryBits == Constants.C_SPIKE
				|| fb.categoryBits == Constants.C_SPIKE) {
			if(fixA.getUserData() instanceof WhitePlayer){SoundLib.CloudDeathv2.play();
			}else{SoundLib.RockDeath.play();}
			
			
			playerDead = true;
		}

		if (fb.categoryBits == Constants.C_WHITEPLAYER
				|| fa.categoryBits == Constants.C_WHITEPLAYER) {
			wpFootCount++;
			if (fixA.getUserData() instanceof WhitePlayer) {
				wpBody = fixA.getBody();
			} else {
				wpBody = fixB.getBody();
			}
		}
		if (fb.categoryBits == Constants.C_BLACKPLAYER
				|| fa.categoryBits == Constants.C_BLACKPLAYER) {
			bpFootCount++;
			if (fixA.getUserData() instanceof BlackPlayer) {
				bpBody = fixA.getBody();
			} else {
				bpBody = fixB.getBody();
			}
		}
		
		if (fb.categoryBits == Constants.C_FINISHING_OBJECT
				|| fa.categoryBits == Constants.C_FINISHING_OBJECT) {
			loodNextLevel = true;
		}

	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		if (fixA == null || fixB == null)
			return;

		Filter fa = fixA.getFilterData();
		Filter fb = fixB.getFilterData();

		if (fb.categoryBits == Constants.C_WHITEPLAYER
				|| fa.categoryBits == Constants.C_WHITEPLAYER) {
			wpFootCount--;
			if (fixA.getUserData() instanceof WhitePlayer) {
				wpBody = fixA.getBody();
			} else {
				wpBody = fixB.getBody();
			}
		}
		if (fb.categoryBits == Constants.C_BLACKPLAYER
				|| fa.categoryBits == Constants.C_BLACKPLAYER) {
			bpFootCount--;
			if (fixA.getUserData() instanceof BlackPlayer) {
				bpBody = fixA.getBody();
			} else {
				bpBody = fixB.getBody();
			}
		}

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	public boolean isPlayerDead() {
		return playerDead;
	}

	public boolean isBPGrounded() {
		if(bpBody == null) {
			return false;
		}
		float velY = bpBody.getLinearVelocity().y;
		if (velY > 0.06f || velY < -0.06f) {  //PATCH ALL THE THINGS !!
			bpVelocity = false;
		} else {
			bpVelocity = true;
		}
		return bpFootCount > 0 && bpVelocity;
	}

	public boolean isWPGrounded() {
		if(wpBody == null) {
			return false;
		}
		float velY = wpBody.getLinearVelocity().y;
		if (velY > 0.06f || velY < -0.06f) {
			wpVelocity = false;
		} else {
			wpVelocity = true;
		}
		return wpFootCount > 0 && wpVelocity;
	}

	public boolean loadNextLevel() {
		return loodNextLevel;
	}
}
