package org.shared.domain.exception

import org.shared.domain.vo.ShareId

class ShareNotFoundException(share: ShareId): DomainException("Share ${share.symbol} not found!") {
}